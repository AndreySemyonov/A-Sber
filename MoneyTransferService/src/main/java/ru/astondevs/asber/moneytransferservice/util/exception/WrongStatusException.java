package ru.astondevs.asber.moneytransferservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Appears when transfer has incorrect status.
 *  throws {@link Exception}.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongStatusException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public WrongStatusException() {
        super("Transfer status is not DRAFT");
    }
}
