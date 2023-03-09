package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import ru.astondevs.asber.userservice.controller.UserSettingsController;

import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link UserSettingsController#changeEmail(UUID, EmailDto)} method of {@link UserSettingsController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    /**
     * New email.
     */
    @NotNull
    @Email
    private String newEmail;
}
