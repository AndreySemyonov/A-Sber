package ru.astondevs.asber.moneytransferservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.moneytransferservice.service.ExchangeRateService;

import javax.persistence.PersistenceException;

/**
 * Custom Exception that would be thrown if method {@link ExchangeRateService#getExchangeRate(String)}
 *  throws {@link Exception}.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InfoServiceRequestException extends PersistenceException {

    public InfoServiceRequestException(String message) {
        super(message);
    }
}
