package ru.astondevs.asber.userservice.util.exception;

import lombok.Getter;
import ru.astondevs.asber.userservice.util.ErrorType;

/**
 * Parent UserService exception
 */
@Getter
public class BaseUserServiceException extends RuntimeException {
    private final ErrorType errorType;

    /**
     * Class constructor specifying messages for exception
     */
    public BaseUserServiceException(String message) {
        super(message);
        this.errorType = ErrorType.APP_ERROR;
    }

    /**
     * Class constructor specifying message and {@link ErrorType} for exception
     */
    public BaseUserServiceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
