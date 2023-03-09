package ru.astondevs.asber.userservice.service;


/**
 * Service that works with {@link ru.astondevs.asber.userservice.entity.Verification}.
 */
public interface VerificationService {

    /**
     * Method that creates/updates verification by phone number.
     */
    void createOrUpdateVerification(String mobilePhone);

    /**
     * Method that verifies code.
     */
    void verifyCode(String mobilePhone, String verificationCode);

    /**
     * Method that creates/updates verification by passport number.
     */
    void createOrUpdateVerificationByPassport(String passportNumber);

    /**
     * Method that verifies code.
     */
    void verifyCodeByPassport(String passportNumber, String verificationCode);
}
