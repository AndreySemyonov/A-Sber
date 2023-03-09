package ru.astondevs.asber.userservice.util.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class that stores verification properties.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "verification")
public class VerificationProperties {
    private int codeExpirationInSeconds;
    private int blockExpirationInSeconds;
    private int maxNumberOfAttempts;
}
