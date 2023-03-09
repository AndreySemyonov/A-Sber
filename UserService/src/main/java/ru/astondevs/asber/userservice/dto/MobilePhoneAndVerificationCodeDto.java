package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ru.astondevs.asber.userservice.controller.VerificationController;

/**
 * Data transfer object that is used in response of
 * {@link VerificationController#verifyCode(MobilePhoneAndVerificationCodeDto)} method of {@link VerificationController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MobilePhoneAndVerificationCodeDto {
    /**
     * Mobile phone.
     */
    @NotNull
    @Pattern(regexp = "^\\d{11}$")
    private String mobilePhone;
    /**
     * Verification code.
     */
    @NotNull
    @Pattern(regexp = "^\\d{6}$")
    private String verificationCode;
}
