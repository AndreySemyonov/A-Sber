package ru.astondevs.asber.apigateway.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.apigateway.webclient.AuthenticationClient;

/**
 * Data transfer object that is used in response of {@link AuthenticationClient#refreshAccessToken()}
 * method of {@link AuthenticationClient}.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
@ToString
public class AccessTokenDto {
    /**
     * AccessToken received from JWT.
     */
    private String accessToken;
}
