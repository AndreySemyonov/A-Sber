package ru.astondevs.asber.userservice.service;

/**
 * Service that works with {@link ru.astondevs.asber.userservice.entity.Verification}.
 */
public interface OtpService {
    /**
     * Method that generates OTP
     */
    String generateOtp();
}
