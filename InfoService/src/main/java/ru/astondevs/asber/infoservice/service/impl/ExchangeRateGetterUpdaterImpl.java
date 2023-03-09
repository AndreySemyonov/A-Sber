package ru.astondevs.asber.infoservice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.dto.OuterApiExchangeRateDto;
import ru.astondevs.asber.infoservice.service.ExchangeRateGetterFromOuterApi;
import ru.astondevs.asber.infoservice.service.ExchangeRateUpdater;
import ru.astondevs.asber.infoservice.util.exception.CannotGetExchangeRateFromOuterApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateGetterUpdaterImpl implements ExchangeRateUpdater,
    ExchangeRateGetterFromOuterApi {

    private final List<String> currencies = List.of("USD", "EUR", "CNY");

    private final ExchangeRateKafkaServiceImpl usdExchangeRateKafkaService;

    /**
     * Method that performs get request to outer api.
     *
     * @return {@link OuterApiExchangeRateDto}
     */
    private OuterApiExchangeRateDto getFromOuterApi(String currencyCode) {
        log.info("Getting exchange rate for {} from OUTER API", currencyCode);
        final String URL = String.format("https://api.exchangerate.host/latest?base=%s",
            currencyCode);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        OuterApiExchangeRateDto outerExchangeRate = restTemplate.getForObject(URL,
            OuterApiExchangeRateDto.class);

        log.info("Got exchange rate from outer api, {}", outerExchangeRate.getBase());
        return outerExchangeRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExchangeRateDto getExchangeRate(String currencyCode)
        throws CannotGetExchangeRateFromOuterApiException {
        OuterApiExchangeRateDto outerApiExchangeRateDto = getFromOuterApi(currencyCode);
        log.info("Converting to exchangeRateDto");
        try {
            BigDecimal sellingRate = BigDecimal.valueOf(
                outerApiExchangeRateDto.getRates().get("RUB"));
            sellingRate = sellingRate.setScale(4, RoundingMode.HALF_UP);

            BigDecimal buyingRate = sellingRate.multiply(BigDecimal.valueOf(0.9));
            buyingRate = buyingRate.setScale(4, RoundingMode.HALF_UP);

            ExchangeRateDto exchangeRateDto = ExchangeRateDto.builder()
                .sellingRate(sellingRate.toString())
                .buyingRate(buyingRate.toString())
                .updateAt(LocalDateTime.now())
                .unit(1)
                .name(outerApiExchangeRateDto.getBase())
                .currencyCode(outerApiExchangeRateDto.getBase())
                .build();
            log.info("Returning converted exchange rate");
            return exchangeRateDto;
        } catch (NullPointerException exception) {
            throw new CannotGetExchangeRateFromOuterApiException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void updateExchangeRates() {
        try {
            for (String currencyCode : currencies) {
                log.info("Updating {} exchange rate", currencyCode);
                ExchangeRateDto exchangeRate = getExchangeRate(currencyCode);
                usdExchangeRateKafkaService.send(exchangeRate);
            }
        } catch (CannotGetExchangeRateFromOuterApiException exception) {
            log.error("Cannot update exchange rate");
        }
    }
}
