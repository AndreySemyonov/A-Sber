package ru.astondevs.asber.userservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.UserController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Data transfer object that is used in response of
 * {@link UserController#getPhoneByPassportNumber(PassportNumberDto)} method of {@link UserController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PassportNumberDto {
    /**
     * Passport number.
     */
    @Setter(AccessLevel.NONE)
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?!(.*\\s){2})(?!(.*-){2})[A-Z0-9 -]{6,10}$")
    private String passportNumber;

    /**
     * Method that sets passport number.
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
