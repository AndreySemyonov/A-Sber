package ru.astondevs.asber.userservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.UserController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Data transfer object that is used in response of
 * {@link UserController#registerNotClient(RegisterNotClientDto)} method of {@link UserController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterNotClientDto {
    /**
     * Mobile phone.
     */
    @NotNull
    @Pattern(regexp = "^\\d{11}$")
    private String mobilePhone;
    /**
     * Password.
     */
    @NotNull
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w]{6,20}$")
    private String password;
    /**
     * Security question.
     */
    @NotNull
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w ]{0,50}$")
    private String securityQuestion;
    /**
     * Answer for security question.
     */
    @Setter(AccessLevel.NONE)
    @NotNull
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w ]{0,50}$")
    private String securityAnswer;
    /**
     * Email.
     */
    @Setter(AccessLevel.NONE)
    @Email
    private String email;
    /**
     * User's firstname.
     */
    @NotNull
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w ]{2,30}$")
    private String firstName;
    /**
     * User's middlename.
     */
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w ]{2,30}$")
    private String middleName;
    /**
     * User's lastname.
     */
    @NotNull
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w ]{2,30}$")
    private String lastName;
    /**
     * Passport number.
     */
    @Setter(AccessLevel.NONE)
    @NotNull
    // TODO почему здесь такой странный паттерн?
    @Pattern(regexp = "^[[!\\\\\"#$%&'()*+,-./:;<=>?@\\[\\]^`{|}~][а-яА-Я]\\d\\w ]{2,30}$")
    private String passportNumber;
    /**
     * Country of residence.
     */
    @NotBlank
    private String countryOfResidence;

    /**
     * Method that sets email.
     */
    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    /**
     * Method that sets passport number.
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber.toLowerCase();
    }

    /**
     * Method that sets security answer.
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer.toLowerCase();
    }
}
