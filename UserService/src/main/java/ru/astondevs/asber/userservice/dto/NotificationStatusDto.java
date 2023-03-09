package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.UserSettingsController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link UserSettingsController#changeEmailSubscriptionNotification(UUID, NotificationStatusDto)}
 * method of {@link UserSettingsController},
 * {@link UserSettingsController#changeSmsNotification(UUID, NotificationStatusDto)} method of {@link UserSettingsController},
 * {@link UserSettingsController#changePushNotification(UUID, NotificationStatusDto)} method of {@link UserSettingsController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationStatusDto {
    /**
     * Notification status.
     */
    @NotNull
    private Boolean notificationStatus;
}
