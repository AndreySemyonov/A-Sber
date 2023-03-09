package ru.astondevs.asber.infoservice.util.exception;

import ru.astondevs.asber.infoservice.service.ExchangeRateGetterFromOuterApi;

/**
 * Would be thrown if get request returns null in {@link ExchangeRateGetterFromOuterApi#getExchangeRate()}
 */
public class CannotGetExchangeRateFromOuterApiException extends RuntimeException{

    public CannotGetExchangeRateFromOuterApiException() {
        super("Cannot get exchange rate from outer api");
    }
}
