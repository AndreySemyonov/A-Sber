package ru.astondevs.asber.apigateway.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.apigateway.webclient.AuthenticationClient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Data transfer object that is used in request of {@link AuthenticationClient#login(LoginRequestDto)}
 * method of {@link AuthenticationClient}.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
@ToString
public class LoginRequestDto {
    /**
     * User login.
     */
    @NotBlank
    private String login;

    /**
     * User password.
     */
    @NotNull
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w]{6,20}$")
    private String password;

    /**
     * Type of login should be equals PHONE_NUMBER/PASSPORT_NUMBER.
     */
    @NotNull
    private String type;
}
