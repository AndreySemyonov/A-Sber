package ru.astondevs.asber.userservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.userservice.util.ErrorType;

/**
 * Class that throws exception if user exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsException extends BaseUserServiceException {

    /**
     * Class constructor specifying messages and {@link ErrorType} for exception
     */
    public UserExistsException() {
        super("User already registered", ErrorType.USER_EXISTS);
    }
}

