package ru.astondevs.asber.infoservice.service;

import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.util.exception.CannotGetExchangeRateFromOuterApiException;

/**
 * Class that gets currency exchange rate from outer api.
 */
public interface ExchangeRateGetterFromOuterApi {

    /**
     * Method that gets currency exchange rate from outer api. After getting currency exchange rate
     * converts it to {@link ExchangeRateDto}.
     *
     * @param currencyCode currencyCode(ex. USD)
     * @return {@link ExchangeRateDto}
     * @throws CannotGetExchangeRateFromOuterApiException if outer api returns null.
     */
    ExchangeRateDto getExchangeRate(String currencyCode)
        throws CannotGetExchangeRateFromOuterApiException;

}
