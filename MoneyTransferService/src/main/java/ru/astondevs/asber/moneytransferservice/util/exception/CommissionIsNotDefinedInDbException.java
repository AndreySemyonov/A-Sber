package ru.astondevs.asber.moneytransferservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Would be thrown if a value of a commission is defined incorrectly in DB
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CommissionIsNotDefinedInDbException extends RuntimeException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CommissionIsNotDefinedInDbException() {
        super("CommissionFix and PercentCommission values were not defined for this transfer. Please report.");
    }
}
