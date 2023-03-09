package ru.astondevs.asber.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.userservice.util.properties.VerificationProperties;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.service.ContactsService;
import ru.astondevs.asber.userservice.service.OtpService;
import ru.astondevs.asber.userservice.service.VerificationService;
import ru.astondevs.asber.userservice.util.VerificationValidator;
import ru.astondevs.asber.userservice.util.exception.ClientBlockedException;
import ru.astondevs.asber.userservice.util.exception.InvalidVerificationCodeException;
import ru.astondevs.asber.userservice.util.exception.InvalidVerificationException;

import java.time.LocalDateTime;

/**
 * Implementation of {@link VerificationService}.
 * Works with {@link ContactsService}, {@link VerificationService}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final ContactsService contactsService;
    private final OtpService otpService;
    private final VerificationValidator verificationValidator;
    private final VerificationProperties verificationProperties;

    /**
     * {@inheritDoc}
     * For creating/updating verification uses method {@link ContactsService#findContactsWithVerificationByMobilePhone(String)}.
     */
    @Override
    @Transactional
    public void createOrUpdateVerification(String mobilePhone) {
        log.info("Creating/updating verification for mobile phone: {}", mobilePhone);
        Contacts contacts = contactsService.findContactsWithVerificationByMobilePhone(mobilePhone);

        verificationValidator.checkVerificationBlockedAndNotExpired(contacts.getVerification());

        contacts.getVerification().setSmsVerificationCode(otpService.generateOtp());
        contacts.getVerification().setSmsCodeExpiration(LocalDateTime.now()
                .plusSeconds(verificationProperties.getCodeExpirationInSeconds()));
        contacts.getVerification().setVerificationAttempts(0);
        contacts.getVerification().setBlockExpiration(null);
        log.info("Created/updated verification for mobile phone: {}", mobilePhone);
    }

    /**
     * {@inheritDoc}
     * For verifying code uses method {@link ContactsService#findContactsWithVerificationByMobilePhone(String)}.
     */
    @Override
    @Transactional(noRollbackFor =
            {InvalidVerificationCodeException.class, ClientBlockedException.class, InvalidVerificationException.class}
    )
    public void verifyCode(String mobilePhone, String verificationCode) {
        log.info("Verifying code for mobile phone: {}", mobilePhone);
        Contacts contacts = contactsService.findContactsWithVerificationByMobilePhone(mobilePhone);
        verificationValidator.checkVerificationValidity(contacts.getVerification());
        verificationValidator.checkVerificationBlocked(contacts.getVerification());

        contacts.getVerification().incrementAttempts();

        verificationValidator.checkVerificationCode(contacts.getVerification(), verificationCode);

        verificationValidator.invalidateVerification(contacts);
        log.info("Verified code for mobile phone: {}", mobilePhone);
    }

    /**
     * {@inheritDoc}
     * For creating/updating verification uses method {@link ContactsService#getPhoneByPassportNumber(String)}
     * and {@link #createOrUpdateVerification(String)}.
     */
    @Override
    @Transactional
    public void createOrUpdateVerificationByPassport(String passportNumber) {
        log.info("Request for phone by passport number: {}", passportNumber);
        String mobileNumber = contactsService.getPhoneByPassportNumber(passportNumber).getMobilePhone();
        createOrUpdateVerification(mobileNumber);
    }

    /**
     * {@inheritDoc}
     * For verifying code uses method {@link ContactsService#getPhoneByPassportNumber(String)}
     * * and {@link #verifyCode(String, String)}.
     */
    @Override
    @Transactional(noRollbackFor =
            {InvalidVerificationCodeException.class, ClientBlockedException.class, InvalidVerificationException.class}
    )
    public void verifyCodeByPassport(String passportNumber, String verificationCode) {
        log.info("Request for phone by passport number: {}", passportNumber);
        String mobileNumber = contactsService
                .getPhoneByPassportNumber(passportNumber).getMobilePhone();
        verifyCode(mobileNumber, verificationCode);
    }
}
