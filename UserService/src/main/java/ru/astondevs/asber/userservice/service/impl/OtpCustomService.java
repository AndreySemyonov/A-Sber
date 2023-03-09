package ru.astondevs.asber.userservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astondevs.asber.userservice.service.OtpService;

import java.security.SecureRandom;
import java.util.stream.Collectors;

/**
 * Implementation of {@link OtpService}.
 */
@Slf4j
@Service
public class OtpCustomService implements OtpService {

    /**
     * {@inheritDoc}
     * For generating OTP.
     */
    @Override
    public String generateOtp() {
        log.info("Generating OTP");
        String otp = new SecureRandom().ints(6, 0, 10).mapToObj(Integer::toString)
                .collect(Collectors.joining());

        log.info("OTP generated");
        return otp;
    }
}
