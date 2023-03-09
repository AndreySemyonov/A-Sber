package ru.astondevs.asber.moneytransferservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.moneytransferservice.dto.ExchangeRateDto;
import ru.astondevs.asber.moneytransferservice.service.ExchangeRateService;

/**
 * Controller for exchange-rates requests
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    /**
     * Endpoint for getting exchange rate for currency
     * @param currencyCodeFrom - currency code (3 letters, ex. USD)
     * @param currencyCodeTo - currency code (3 letters, ex. USD)
     */
    @GetMapping("/rates")
    @ApiOperation(value = "Get exchange rates",
            authorizations = {@Authorization(value = "Authorization")})
    ExchangeRateDto getExchangeRate(@RequestParam String currencyCodeFrom, String currencyCodeTo) {
        log.info("Request for exchange rate from currency {} to currency {}", currencyCodeFrom, currencyCodeTo);
        ExchangeRateDto exchangeRateDto = exchangeRateService.getExchangeRate(currencyCodeFrom, currencyCodeTo);
        log.info("Returning exchange rate from currency {} to currency {}", currencyCodeFrom, currencyCodeTo);
        return exchangeRateDto;
    }
}
