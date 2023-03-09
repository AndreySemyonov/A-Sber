package ru.astondevs.asber.userservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.astondevs.asber.userservice.repository.VerificationRepository;
import ru.astondevs.asber.userservice.util.properties.VerificationProperties;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.Verification;
import ru.astondevs.asber.userservice.util.exception.ClientBlockedException;
import ru.astondevs.asber.userservice.util.exception.InvalidVerificationCodeException;
import ru.astondevs.asber.userservice.util.exception.InvalidVerificationException;

import java.time.LocalDateTime;

/**
 * Verification validator for check verification code
 */
@Component
@RequiredArgsConstructor
public class VerificationValidator {

    private final VerificationProperties verificationProperties;
    private final VerificationRepository verificationRepository;

    /**
     * Method checks verification code validity
     *
     * @param verification {@link Verification}
     * @throws InvalidVerificationException if verification code wasn't created or expired
     */
    public void checkVerificationValidity(Verification verification) {
        if (isVerificationExists(verification)) {
            throw new InvalidVerificationException("Verification hasn't been created");
        }

        if (isVerificationCodeExpired(verification)) {
            throw new InvalidVerificationException("Verification code has expired");
        }
    }

    /**
     * Method checks if verification code is blocked
     *
     * @param verification {@link Verification}
     * @throws ClientBlockedException           if verification is blocked
     * @throws InvalidVerificationCodeException if verification code is invalid
     */
    public void checkVerificationBlocked(Verification verification) {
        if (wasVerificationBlocked(verification)) {
            // if verification is still blocked
            if (verification.getBlockExpiration().isAfter(LocalDateTime.now())) {
                throw new ClientBlockedException(verification.getRemainingBlockSeconds());
            } else {
                invalidateVerification(verification.getContacts());
                throw new InvalidVerificationException("Verification code is invalid, create new");
            }
        }
    }

    /**
     * Method checks verification code validation
     *
     * @param verification     {@link Verification}
     * @param verificationCode {@link String}
     * @throws ClientBlockedException           if verification is blocked
     * @throws InvalidVerificationCodeException if verification code is invalid
     */
    public void checkVerificationCode(Verification verification, String verificationCode) {
        if (!verification.getSmsVerificationCode().equals(verificationCode)) {
            if (verification.getVerificationAttempts() == verificationProperties.getMaxNumberOfAttempts()) {
                verification.setBlockExpiration(LocalDateTime.now()
                        .plusSeconds(verificationProperties.getBlockExpirationInSeconds()));
                throw new ClientBlockedException(verificationProperties.getBlockExpirationInSeconds());
            } else {
                throw new InvalidVerificationCodeException("wrong verification code");
            }
        }
    }

    /**
     * Method checks if verification is blocked and not expired
     *
     * @param verification {@link Verification}
     * @throws ClientBlockedException if client verification is blocked
     */
    public void checkVerificationBlockedAndNotExpired(Verification verification) {
        if (wasVerificationBlocked(verification)) {
            // if verification is still blocked
            if (verification.getBlockExpiration().isAfter(LocalDateTime.now())) {
                throw new ClientBlockedException(verification.getRemainingBlockSeconds());
            }
        }
    }

    /**
     * Method that removes verification
     *
     * @param contacts {@link Contacts}
     */
    public void invalidateVerification(Contacts contacts) {
        verificationRepository.deleteByContacts(contacts);
    }

    /**
     * Method that resets sms verification code
     *
     * @param verification {@link Verification}
     */
    private boolean isVerificationExists(Verification verification) {
        return verification.getSmsVerificationCode() == null;
    }

    /**
     * Method that checks if verification code is expired
     *
     * @param verification {@link Verification}
     */
    private boolean isVerificationCodeExpired(Verification verification) {
        LocalDateTime now = LocalDateTime.now();
        return verification.getSmsCodeExpiration().isEqual(now)
                || verification.getSmsCodeExpiration().isBefore(now);
    }

    /**
     * Method returns whether verification has ever been blocked
     *
     * @param verification {@link Verification}
     */
    private boolean wasVerificationBlocked(Verification verification) {
        return verification.getBlockExpiration() != null;
    }
}
