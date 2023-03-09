package ru.astondevs.asber.userservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PassportVerificationDto {

    /**
     * Passport number.
     */
    @Setter(AccessLevel.NONE)
    @NotNull
    @Pattern(regexp = "^(?!^[A-Z -]*$)[A-Z0-9 -]{6,10}$")
    private String passportNumber;

    /**
     * Verification code.
     */
    @NotNull
    @Pattern(regexp = "^\\d{6}$")
    private String verificationCode;

    /**
     * Method that sets passport number.
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
