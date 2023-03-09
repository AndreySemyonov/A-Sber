package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.AuthenticationController;
import ru.astondevs.asber.userservice.entity.enums.AuthenticationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Data transfer object that is used in response of
 * {@link AuthenticationController#login(RequestLoginDto)} method of {@link AuthenticationController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginDto {
    /**
     * Login.
     */
    @NotBlank
    private String login;
    /**
     * Password.
     */
    @NotNull
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w]{6,20}$")
    private String password;
    /**
     * Enum of authentication types.
     */
    @NotNull
    private AuthenticationType type;
}
