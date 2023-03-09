package ru.astondevs.asber.userservice.util.exception;

import ru.astondevs.asber.userservice.service.impl.VerificationServiceImpl;
import ru.astondevs.asber.userservice.util.ErrorType;

/**
 * Class that throws exception if method {@link VerificationServiceImpl#verifyCode(String, String)}
 * if verification code is invalid.
 */
public class InvalidVerificationCodeException extends BaseUserServiceException {

    /**
     * Class constructor specifying messages and {@link ErrorType} for exception
     */
    public InvalidVerificationCodeException(String message) {
        super(message, ErrorType.VERIFICATION_CODE_EXCEPTION);
    }
}
