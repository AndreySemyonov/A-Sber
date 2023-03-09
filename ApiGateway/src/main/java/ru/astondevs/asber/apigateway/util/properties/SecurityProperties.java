package ru.astondevs.asber.apigateway.util.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Security Properties that gets value from application.properties
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    /**
     * Access secret that uses to decoding AccessToken.
     */
    private String accessSecret;
    /**
     * Refresh secret that uses to decoding RefreshToken.
     */
    private String refreshSecret;
    /**
     * Header that uses as key of authorization header.
     */
    private String header;
}
