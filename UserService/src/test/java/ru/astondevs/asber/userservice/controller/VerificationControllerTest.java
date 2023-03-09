package ru.astondevs.asber.userservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.userservice.ExternalSystemConfig;
import ru.astondevs.asber.userservice.dto.MobilePhoneAndVerificationCodeDto;
import ru.astondevs.asber.userservice.dto.MobilePhoneDto;
import ru.astondevs.asber.userservice.dto.PassportNumberDto;
import ru.astondevs.asber.userservice.dto.PassportVerificationDto;
import ru.astondevs.asber.userservice.util.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {ExternalSystemConfig.PostgresInitializer.class, ExternalSystemConfig.RedisInitializer.class})
@ActiveProfiles("test")
@Transactional
@Rollback
class VerificationControllerTest {
    private static final String URL = "/api/v1/verifications";

    @Autowired
    private MockMvc mockMvc;

    private MobilePhoneDto existingMobilePhoneDto;
    private MobilePhoneDto notExistingMobilePhoneDto;
    private MobilePhoneAndVerificationCodeDto phoneAndVerificationCodeDto;
    private MobilePhoneAndVerificationCodeDto phoneAndWrongVerificationCodeDto;
    private PassportVerificationDto passportVerificationDto;
    private PassportNumberDto passportNumberDto;

    @BeforeAll
    void setUp() {
        existingMobilePhoneDto = MobilePhoneDto.builder()
                .mobilePhone("89111234567").build();

        notExistingMobilePhoneDto = MobilePhoneDto.builder()
                .mobilePhone("11111234567").build();

        phoneAndVerificationCodeDto = MobilePhoneAndVerificationCodeDto.builder()
                .mobilePhone("89111234567")
                .verificationCode("123456")
                .build();

        phoneAndWrongVerificationCodeDto = MobilePhoneAndVerificationCodeDto.builder()
                .mobilePhone("89111234567")
                .verificationCode("111111")
                .build();
        passportVerificationDto = PassportVerificationDto.builder()
                .passportNumber("1234567890")
                .verificationCode("123456")
                .build();
        passportNumberDto = PassportNumberDto.builder()
                .passportNumber("1234567890")
                .build();
    }

    @DisplayName("If phone is valid and client with such a phone exists then create or update verification")
    @Test
    void createOrUpdateVerification() throws Exception {
        mockMvc.perform(
                        post(URL)
                                .content(JsonUtil.writeValue(existingMobilePhoneDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If client with such a phone doesn't exist then return Http 422 status")
    @Test
    void createOrUpdateVerification_shouldReturn422() throws Exception {
        mockMvc.perform(
                        post(URL)
                                .content(JsonUtil.writeValue(notExistingMobilePhoneDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @DisplayName("If client with such a phone exists and verification was created and all data are valid then return Http 200 status")
    @Test
    void verifyCode_shouldReturn200() throws Exception {
        mockMvc.perform(
                        post(URL + "/verify")
                                .content(JsonUtil.writeValue(phoneAndVerificationCodeDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If verification code is wrong then return Http 400 status")
    @Test
    void verifyCode_WithWrongCode_shouldReturn400() throws Exception {
        mockMvc.perform(
                        post(URL + "/verify")
                                .content(JsonUtil.writeValue(phoneAndWrongVerificationCodeDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("If client send wrong verification code for 3 times then client is blocked and return Http 403 status")
    @Test
    void verifyCode_WithWrongCodeFor3Times_shouldReturn403() throws Exception {
        mockMvc.perform(
                        post(URL + "/verify")
                                .content(JsonUtil.writeValue(phoneAndWrongVerificationCodeDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

        mockMvc.perform(
                        post(URL + "/verify")
                                .content(JsonUtil.writeValue(phoneAndWrongVerificationCodeDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

        mockMvc.perform(
                        post(URL + "/verify")
                                .content(JsonUtil.writeValue(phoneAndWrongVerificationCodeDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("If client's passport number is valid then generate code and return status ok")
    @Test
    void generateVerificationByPassportNumber_shouldReturnStatusOk() throws Exception {
        mockMvc.perform(post(URL+"/generate")
                        .content(JsonUtil.writeValue(passportNumberDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If verification code and passport number is valid then return access and refresh tokens")
    @Test
    void verifyCodeByPassportNumber_shouldReturnTokens() throws Exception {
        mockMvc.perform(
                        post(URL + "/verify/passport")
                                .content(JsonUtil.writeValue(passportVerificationDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If verification code and phone number is valid then return access and refresh tokens")
    @Test
    void verifyCodeByPhoneNumber_shouldReturnTokens() throws Exception {
        mockMvc.perform(
                        post(URL + "/verify/phone")
                                .content(JsonUtil.writeValue(phoneAndVerificationCodeDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}