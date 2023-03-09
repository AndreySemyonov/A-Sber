package ru.astondevs.asber.moneytransferservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.moneytransferservice.service.impl.CardServiceImpl;

/**
 * Custom Exception that would be thrown if method {@link CardServiceImpl#getAccountNumberIfCardNumberIsPresent(String)}
 *  throws {@link feign.FeignException.NotFound}.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public NotFoundException() {
        super("Card is not found");
    }
}
