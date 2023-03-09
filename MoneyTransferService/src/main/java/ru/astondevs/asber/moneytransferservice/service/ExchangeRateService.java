package ru.astondevs.asber.moneytransferservice.service;

import ru.astondevs.asber.moneytransferservice.controller.ExchangeRateController;
import ru.astondevs.asber.moneytransferservice.dto.ExchangeRateDto;

/**
 * Service for {@link ExchangeRateController}
 */
public interface ExchangeRateService {
    /**
     * Method that gets account number by card number
     *
     * @param currencyCodeFrom currency code (3 letters, ex. USD)
     * @param currencyCodeTo   currency code (3 letters, ex. USD)
     * @return {@link ExchangeRateDto} that contains account number.
     */
    ExchangeRateDto getExchangeRate(String currencyCodeFrom, String currencyCodeTo);
}
