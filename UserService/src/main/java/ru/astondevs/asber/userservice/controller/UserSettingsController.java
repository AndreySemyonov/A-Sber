package ru.astondevs.asber.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.userservice.dto.ChangePassportDto;
import ru.astondevs.asber.userservice.dto.EmailDto;
import ru.astondevs.asber.userservice.dto.NotificationStatusDto;
import ru.astondevs.asber.userservice.dto.SecurityQuestionDto;
import ru.astondevs.asber.userservice.dto.UserNotificationsDto;
import ru.astondevs.asber.userservice.mapper.ContactsMapper;
import ru.astondevs.asber.userservice.service.ContactsService;
import ru.astondevs.asber.userservice.service.UserProfileService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Controller that handles requests to {@link ContactsService}, {@link UserProfileService}.
 */
@Api("User settings controller")
@Slf4j
@RestController
@RequestMapping("/api/v1/users/settings")
@RequiredArgsConstructor
public class UserSettingsController {
    private final ContactsService contactsService;
    private final UserProfileService userProfileService;
    private final ContactsMapper contactsMapper;

    /**
     * End-point that updates email subscription setting
     * using {@link ContactsService#changeEmailSubscriptionNotification(UUID, Boolean)}.
     *
     * @param clientId              UUID from request parameter
     * @param notificationStatusDto from request body
     */
    @PatchMapping("/notification/email")
    @ApiOperation(value = "Change email subscription notification", authorizations = {@Authorization("Authorization")})
    public void changeEmailSubscriptionNotification(@RequestParam UUID clientId,
                                                    @RequestBody @Valid NotificationStatusDto notificationStatusDto) {
        log.info("Request for change email subscription notification to: {}", notificationStatusDto.getNotificationStatus());
        contactsService.changeEmailSubscriptionNotification(clientId, notificationStatusDto.getNotificationStatus());
        log.info("Email subscription updated for client: {}", clientId);
    }

    /**
     * End-point that updates user email using {@link ContactsService#changeEmail(UUID, String)}.
     *
     * @param clientId UUID from request parameter
     * @param emailDto from request body
     */
    @PatchMapping("/email")
    @ApiOperation(value = "Change email", authorizations = {@Authorization("Authorization")})
    public void changeEmail(@RequestParam UUID clientId, @RequestBody @Valid EmailDto emailDto) {
        log.info("Request by clientId {} for change email to: {}", clientId, emailDto.getNewEmail());
        contactsService.changeEmail(clientId, emailDto.getNewEmail());
        log.info("Email changed for client: {}", clientId);
    }

    /**
     * End-point that updates sms notifications using {@link ContactsService#changeSmsNotificationStatus(UUID, Boolean)}.
     *
     * @param clientId  UUID from request parameter
     * @param statusDto from request body
     */
    @PatchMapping("/notifications/sms")
    @ApiOperation(value = "Change email notification status", authorizations = {@Authorization("Authorization")})
    public void changeSmsNotification(@RequestParam UUID clientId, @RequestBody @Valid NotificationStatusDto statusDto) {
        log.info("Request by clientId {} for change sms notifications to: {}", clientId, statusDto.getNotificationStatus());
        contactsService.changeSmsNotificationStatus(clientId, statusDto.getNotificationStatus());
        log.info("SMS-notification subscription updated for client: {}", clientId);
    }

    /**
     * End-point that updates pust notifications using {@link ContactsService#changePushNotification(UUID, Boolean)}.
     *
     * @param clientId              UUID from request parameter
     * @param notificationStatusDto from request body
     */
    @PatchMapping("/notifications/push")
    @ApiOperation(value = "Change push notification status", authorizations = {@Authorization("Authorization")})
    public void changePushNotification(@RequestParam UUID clientId,
                                       @RequestBody @Valid NotificationStatusDto notificationStatusDto) {
        log.info("Request by clientId {} for change sms notifications to: {}",
                clientId, notificationStatusDto.getNotificationStatus());
        contactsService.changePushNotification(clientId, notificationStatusDto.getNotificationStatus());
        log.info("Push-notification subscription updated for client: {}", clientId);
    }

    /**
     * End-point that gets refreshed token using {@link ContactsService#getUserNotifications(UUID)}.
     *
     * @param clientId UUID from request parameter
     * @return {@link UserNotificationsDto} with all data about notification settings
     */
    @GetMapping("/notifications/all")
    @ApiOperation(value = "Get user's notifications", authorizations = {@Authorization("Authorization")})
    public UserNotificationsDto getUserNotifications(@RequestParam UUID clientId) {
        log.info("Request for all notification settings of user with clientId: {}", clientId);
        UserNotificationsDto userNotificationsDto = contactsMapper
                .toUserNotificationsDto(contactsService.getUserNotifications(clientId));
        log.info("Return notification settings info for client: {}", clientId);
        return userNotificationsDto;
    }

    /**
     * End-point that updates user password using {@link UserProfileService#changePassword(UUID, String, String)}.
     *
     * @param clientId UUID from request parameter
     * @param changePassportDto from request body
     */
    @PatchMapping("/password")
    @ApiOperation(value = "Change password", authorizations = {@Authorization("Authorization")})
    public void changePassword(@RequestParam UUID clientId, @RequestBody @Valid ChangePassportDto changePassportDto) {
        log.info("Request for update password for clientId: {}", clientId);
        userProfileService.changePassword(clientId, changePassportDto.getOldPassword(), changePassportDto.getNewPassword());
        log.info("password changed for client: {}", clientId);
    }

    /**
     * End-point that updates user security question and answer
     * using {@link UserProfileService#changeSecretQuestionAndAnswer(UUID, String, String)}.
     *
     * @param clientId            UUID from request parameter
     * @param securityQuestionDto from request body
     */
    @PatchMapping("/controls")
    @ApiOperation(value = "Change security question", authorizations = {@Authorization("Authorization")})
    public void changeSecurityQuestion(@RequestParam UUID clientId, @RequestBody @Valid SecurityQuestionDto securityQuestionDto) {
        log.info("Request for update security question for clientId: {}", clientId);
        userProfileService.changeSecretQuestionAndAnswer(clientId, securityQuestionDto.getSecurityQuestion(),
                securityQuestionDto.getSecurityAnswer());
        log.info("Security question updated for clientId: {}", clientId);
    }
}
