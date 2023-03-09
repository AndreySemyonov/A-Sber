package ru.astondevs.asber.userservice.util.exception;

import ru.astondevs.asber.userservice.dto.FingerprintDto;
import ru.astondevs.asber.userservice.service.impl.AuthenticationServiceImpl;
import ru.astondevs.asber.userservice.util.ErrorType;

import java.util.UUID;

/**
 * Class that throws exception if method {@link AuthenticationServiceImpl#loginByPin(UUID, FingerprintDto, String)}
 * if fingerprint is invalid.
 */
public class InvalidFingerprintException extends BaseUserServiceException {

    /**
     * Class constructor specifying messages and {@link ErrorType} for exception
     */
    public InvalidFingerprintException(String message) {
        super(message, ErrorType.INVALID_FINGERPRINT);
    }
}
