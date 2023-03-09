package ru.astondevs.asber.moneytransferservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.moneytransferservice.controller.ExchangeRateController;
import ru.astondevs.asber.moneytransferservice.dto.ExchangeRateDto;
import ru.astondevs.asber.moneytransferservice.service.ExchangeRateService;
import ru.astondevs.asber.moneytransferservice.service.InfoServiceClient;
import ru.astondevs.asber.moneytransferservice.util.exception.InfoServiceRequestException;

import java.util.HashMap;
import java.util.Map;

/**
 * Service implementation of {@link ExchangeRateService} for {@link ExchangeRateController}
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final InfoServiceClient infoServiceClient;

    private static String err = "Unable to get currency code from info-service";

    @Override
    public ExchangeRateDto getExchangeRate(String currencyCodeFrom, String currencyCodeTo) {
        try {
            Map<String, String> currencyCodeJson = new HashMap<>();
            currencyCodeJson.put("currencyCode", currencyCodeFrom);
            log.info("Request for exchange rate for from currency {}", currencyCodeFrom);
            ExchangeRateDto exchangeRateFromDto = infoServiceClient.getExchangeRate(currencyCodeJson);
            currencyCodeJson.put("currencyCode", currencyCodeTo);
            log.info("Request for exchange rate for to currency {}", currencyCodeTo);
            ExchangeRateDto exchangeRateToDto = infoServiceClient.getExchangeRate(currencyCodeJson);
            log.info("Calculating exchange rate value...");
            Double calculatedValue = (Double.parseDouble(exchangeRateFromDto.getBuyingRate())/exchangeRateFromDto.getUnit())
                    /(Double.parseDouble(exchangeRateToDto.getSellingRate())/exchangeRateToDto.getUnit());
            log.info("Calculated exchange rate value: {}", calculatedValue);
            return ExchangeRateDto
                    .builder()
                    .currencyCode(currencyCodeTo)
                    .buyingRate(String.valueOf(calculatedValue))
                    .sellingRate(exchangeRateToDto.getSellingRate())
                    .unit(exchangeRateToDto.getUnit())
                    .build();
        } catch (Exception e) {
            log.error(err);
            throw new InfoServiceRequestException(err);
        }
    }
}
