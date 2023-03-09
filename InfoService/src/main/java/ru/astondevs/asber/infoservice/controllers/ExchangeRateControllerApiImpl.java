package ru.astondevs.asber.infoservice.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.infoservice.controller.ExchangeRateControllerApi;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.dto.GetExchangeRateRequest;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;
import ru.astondevs.asber.infoservice.mapper.ExchangeRateMapper;
import ru.astondevs.asber.infoservice.service.ExchangeRateService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExchangeRateControllerApiImpl implements ExchangeRateControllerApi {

    private final ExchangeRateService exchangeRateService;

    private final ExchangeRateMapper exchangeRateMapper;

    @Override
    public ResponseEntity<ExchangeRateDto> getExchangeRate(
        GetExchangeRateRequest getExchangeRateRequest) {
        String currencyCode = getExchangeRateRequest.getCurrencyCode();
        log.info("Request for exchange rate for currency with currency code: {}", currencyCode);

        ExchangeRate exchangeRate = exchangeRateService.getExchangeRateByCurrencyCode(currencyCode);
        ExchangeRateDto exchangeRateDto = exchangeRateMapper.toExchangeRateDto(exchangeRate);

        log.info("Returning exchange rate for currency with currency code: {}", currencyCode);
        return ResponseEntity.ok(exchangeRateDto);
    }

    @Override
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRates() {
        log.info("Request for all exchange rates");
        List<ExchangeRate> exchangeRates = exchangeRateService.getAllExchangeRates();
        List<ExchangeRateDto> exchangeRateDtoList = exchangeRateMapper.toExchangeRates(
            exchangeRates);
        log.info("Returning exchange rate list");
        return ResponseEntity.ok(exchangeRateDtoList);
    }

}
