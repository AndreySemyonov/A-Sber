package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.UserSettingsController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link UserSettingsController#changePassword(UUID, ChangePassportDto)} method of {@link UserSettingsController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassportDto {
    /**
     * Old password.
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String oldPassword;
    /**
     * New password.
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String newPassword;
}
