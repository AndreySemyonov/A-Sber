package ru.astondevs.asber.userservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.astondevs.asber.userservice.ExternalSystemConfig;
import ru.astondevs.asber.userservice.dto.ChangePassportDto;
import ru.astondevs.asber.userservice.dto.EmailDto;
import ru.astondevs.asber.userservice.dto.NotificationStatusDto;
import ru.astondevs.asber.userservice.dto.SecurityQuestionDto;
import ru.astondevs.asber.userservice.dto.UserNotificationsDto;
import ru.astondevs.asber.userservice.util.JsonUtil;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
public class UserSettingsControllerTest {

    private final String URL = "/api/v1/users/settings";

    private EmailDto validEmailDto;
    private NotificationStatusDto trueNotificationStatus;
    private UserNotificationsDto userNotificationsDto;
    private ChangePassportDto changePassportDto;
    private ChangePassportDto notValidChangePassportDto;
    private SecurityQuestionDto securityQuestionDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        trueNotificationStatus = NotificationStatusDto.builder()
                .notificationStatus(true)
                .build();

        validEmailDto = EmailDto.builder().newEmail("some_user@yandex.ru")
                .build();

        validEmailDto = EmailDto.builder()
                .newEmail("some_user@yandex.ru")
                .build();

        userNotificationsDto = UserNotificationsDto.builder()
                .email("petrov_petr@mail.ru")
                .smsNotification(false)
                .pushNotification(false)
                .emailSubscription(false)
                .build();

        changePassportDto = ChangePassportDto.builder()
                .oldPassword("password")
                .newPassword("password")
                .build();

        notValidChangePassportDto = ChangePassportDto.builder()
                .oldPassword("notValidPassport")
                .newPassword("newPassword")
                .build();

        securityQuestionDto = SecurityQuestionDto.builder()
                .securityQuestion("What is my favourite movie")
                .securityAnswer("Untouchable")
                .build();
    }

    @Test
    @DisplayName("if contacts found by client id then we change email subscription")
    void changeEmailSubscriptionNotification_shouldChangeEmailSubscriptionNotification() throws Exception {
        final String changeEmailSubscriptionNotificationUrl = String.format("%s/notification/email", URL);

        mockMvc.perform(patch(changeEmailSubscriptionNotificationUrl)
                        .queryParam("clientId", "695aecfe-152f-421d-9c4b-5831b812cd2a")
                        .content(JsonUtil.writeValue(trueNotificationStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user found by clientId his contacts then we are changing email")
    void changeEmail_shouldChangeEmail() throws Exception {
        final String changeEmailUrl = String.format("%s/email", URL);

        mockMvc.perform(patch(changeEmailUrl)
                        .queryParam("clientId", "798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
                        .content(JsonUtil.writeValue(validEmailDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("if contacts found by client id then we change push notification")
    void changePushNotification_shouldChangePushNotification() throws Exception {
        final String changePushNotificationUrl = String.format("%s/notifications/push", URL);

        mockMvc.perform(patch(changePushNotificationUrl)
                        .queryParam("clientId", "798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
                        .content(JsonUtil.writeValue(trueNotificationStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If client was found by id and notificationStatus is valid boolean value then change status")
    void changeSmsNotification_shouldChangeSmsNotification() throws Exception {
        final String changeSmsNotificationUrl = String.format("%s/notifications/sms", URL);

        mockMvc.perform(patch(changeSmsNotificationUrl)
                        .queryParam("clientId", "798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
                        .content(JsonUtil.writeValue(trueNotificationStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If client wasn't found by id then return Http 422 status")
    void changeSmsNotification_shouldReturn422() throws Exception {
        final String changeSmsNotificationUrl = String.format("%s/notifications/sms", URL);

        mockMvc.perform(patch(changeSmsNotificationUrl)
                        .queryParam("clientId", String.valueOf(UUID.randomUUID()))
                        .content(JsonUtil.writeValue(trueNotificationStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @DisplayName("If user found by clientId his contacts then we are returning his settings of notifications")
    void getUserNotifications_shouldGetUserNotificationsDto() throws Exception {
        final String getUserNotificationsUrl = String.format("%s/notifications/all", URL);

        mockMvc.perform(get(getUserNotificationsUrl)
                        .queryParam("clientId", "695aecfe-152f-421d-9c4b-5831b812cd2a")
                        .content(JsonUtil.writeValue(userNotificationsDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(userNotificationsDto.getEmail()))
                .andExpect(jsonPath("$.pushNotification").value(userNotificationsDto.isPushNotification()))
                .andExpect(jsonPath("$.pushNotification").value(userNotificationsDto.isPushNotification()))
                .andExpect(jsonPath("$.emailSubscription").value(userNotificationsDto.isEmailSubscription()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user found by clientId and ChangePassportDto is valid then we are changing password")
    void changePassword_shouldChangePassword() throws Exception {
        final String changePasswordUrl = String.format("%s/password", URL);

        mockMvc.perform(patch(changePasswordUrl)
                        .queryParam("clientId", "695aecfe-152f-421d-9c4b-5831b812cd2a")
                        .content(JsonUtil.writeValue(changePassportDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user found by clientId but ChangePassportDto is not valid then return Http 400 status")
    void changePassword_shouldChangePasswordReturn400() throws Exception {
        final String changePasswordUrl = String.format("%s/password", URL);

        mockMvc.perform(patch(changePasswordUrl)
                        .queryParam("clientId", "695aecfe-152f-421d-9c4b-5831b812cd2a")
                        .content(JsonUtil.writeValue(notValidChangePassportDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wasn't found by clientId then return Http 422 status")
    void changePassword_shouldChangePasswordReturn422() throws Exception {
        final String changePasswordUrl = String.format("%s/password", URL);

        mockMvc.perform(patch(changePasswordUrl)
                        .queryParam("clientId", String.valueOf(UUID.randomUUID()))
                        .content(JsonUtil.writeValue(changePassportDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @DisplayName("If user profile found by clientId then we should change security question and answer")
    void changeUserSecurityQuestionAndAnswer_ShouldChangeUserSecurityQuestionAndAnswer() throws Exception {
        final String changeUserSecurityQuestionAndAnswerUrl = String.format("%s/controls", URL);

        mockMvc.perform(patch(changeUserSecurityQuestionAndAnswerUrl)
                        .queryParam("clientId", "798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
                        .content(JsonUtil.writeValue(securityQuestionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

