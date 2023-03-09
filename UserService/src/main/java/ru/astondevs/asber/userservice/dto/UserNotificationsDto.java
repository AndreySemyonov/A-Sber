package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.UserSettingsController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link UserSettingsController#getUserNotifications(UUID)} method of {@link UserSettingsController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationsDto {
    /**
     * Email.
     */
    @Email
    private String email;
    /**
     * SMS-notification setting.
     */
    @NotNull
    private boolean smsNotification;
    /**
     * Push-notification setting.
     */
    @NotNull
    private boolean pushNotification;
    /**
     * Email-notification setting.
     */
    @NotNull
    private boolean emailSubscription;
}
