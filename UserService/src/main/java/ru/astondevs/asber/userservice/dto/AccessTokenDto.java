package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.AuthenticationController;

import javax.validation.constraints.NotBlank;

/**
 * Data transfer object that is used in response of
 * {@link AuthenticationController#refreshAccessToken(String)} method of {@link AuthenticationController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {
    /**
     * Access token.
     */
    @NotBlank
    private String accessToken;
}
