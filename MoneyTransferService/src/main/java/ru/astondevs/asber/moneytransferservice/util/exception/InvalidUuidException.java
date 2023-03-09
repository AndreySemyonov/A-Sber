package ru.astondevs.asber.moneytransferservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.moneytransferservice.service.impl.TransferServiceImpl;

/**
 * Custom Exception that would be thrown if method {@link TransferServiceImpl#getClientOperationsHistory(String)}
 *  throws {@link IllegalArgumentException}.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUuidException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public InvalidUuidException() {
        super("Invalid UUID parameter");
    }
}
