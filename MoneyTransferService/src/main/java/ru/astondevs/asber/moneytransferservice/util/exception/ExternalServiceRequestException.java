package ru.astondevs.asber.moneytransferservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.moneytransferservice.service.impl.CardServiceImpl;

/**
 * Custom Exception that would be thrown if method {@link CardServiceImpl#getAccountNumberIfCardNumberIsPresent(String)}
 *  throws {@link Exception}.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExternalServiceRequestException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public ExternalServiceRequestException() {
        super("Can not check card number");
    }
}
