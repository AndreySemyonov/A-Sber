package ru.astondevs.asber.userservice.util.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class that stores security properties.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private int accessTokenTimeoutInMinutes;
    private int refreshTokenTimeoutInMinutes;
    private String accessSecret;
    private String refreshSecret;
}
