package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.VerificationController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Data transfer object that is used in response of
 * {@link VerificationController#createOrUpdateVerification(MobilePhoneDto)} method of {@link VerificationController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MobilePhoneDto {
    /**
     * Mobile phone.
     */
    @NotNull
    @Pattern(regexp = "^\\d{11}$")
    private String mobilePhone;
}
