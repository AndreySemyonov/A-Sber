package ru.astondevs.asber.apigateway.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Jwt Exception that informs about invalid token.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidJwtException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public InvalidJwtException() {
        super("jwt is invalid");
    }
}
