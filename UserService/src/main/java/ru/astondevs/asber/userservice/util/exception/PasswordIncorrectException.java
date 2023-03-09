package ru.astondevs.asber.userservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.userservice.util.ErrorType;

/**
 * Class that throws exception if password is incorrect.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordIncorrectException extends BaseUserServiceException {

    /**
     * Class constructor specifying messages and {@link ErrorType} for exception
     */
    public PasswordIncorrectException() {
        super("Incorrect password", ErrorType.PASSWORD_INCORRECT);
    }
}
