package ru.astondevs.asber.userservice.util.exception;

import ru.astondevs.asber.userservice.service.impl.VerificationServiceImpl;
import ru.astondevs.asber.userservice.util.ErrorType;

/**
 * Class that throws exception if method {@link VerificationServiceImpl#verifyCode(String, String)}
 * if verification is invalid.
 */
public class InvalidVerificationException extends BaseUserServiceException {

    /**
     * Class constructor specifying messages and {@link ErrorType} for exception
     */
    public InvalidVerificationException(String msg) {
        super(msg, ErrorType.VERIFICATION_EXCEPTION);
    }
}
