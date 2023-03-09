package ru.astondevs.asber.userservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum that store error type.
 */
@Getter
@AllArgsConstructor
public enum ErrorType {

    ENTITY_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY),
    APP_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTS(HttpStatus.CONFLICT),
    MAIL_NOT_FOUND(HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST),
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST),
    INVALID_FINGERPRINT(HttpStatus.BAD_REQUEST),
    CLIENT_BLOCKED_EXCEPTION(HttpStatus.FORBIDDEN),
    VERIFICATION_EXCEPTION(HttpStatus.UNPROCESSABLE_ENTITY),
    VERIFICATION_CODE_EXCEPTION(HttpStatus.BAD_REQUEST),
    INVALID_JWT(HttpStatus.BAD_REQUEST),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;
}
