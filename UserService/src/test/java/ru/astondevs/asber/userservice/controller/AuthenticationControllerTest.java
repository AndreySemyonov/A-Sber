package ru.astondevs.asber.userservice.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.astondevs.asber.userservice.ExternalSystemConfig;
import ru.astondevs.asber.userservice.dto.FingerprintDto;
import ru.astondevs.asber.userservice.dto.RequestLoginDto;
import ru.astondevs.asber.userservice.entity.enums.AuthenticationType;
import ru.astondevs.asber.userservice.util.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {ExternalSystemConfig.PostgresInitializer.class, ExternalSystemConfig.RedisInitializer.class})
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    private final String URL = "/api/v1/login";
    private static final String EXISTING_CLIENT_ID = "798060ff-ed9b-4745-857c-a7a2f4a2e3a2";
    private static final String NOT_EXISTING_CLIENT_ID = "798060ff-ed9b-4745-857c-a7a2f4a2e3a1";

    private FingerprintDto validFingerprintDto;
    private FingerprintDto invalidFingerprintDto;
    private RequestLoginDto validRequestLoginByPhoneDto;
    private RequestLoginDto validRequestLoginByPassportDto;
    private RequestLoginDto invalidRequestLoginByPassportDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        validRequestLoginByPhoneDto = RequestLoginDto.builder()
                .password("password")
                .login("89111234567")
                .type(AuthenticationType.PHONE_NUMBER)
                .build();
        validRequestLoginByPassportDto = RequestLoginDto.builder()
                .password("password")
                .login("1234567890")
                .type(AuthenticationType.PASSPORT_NUMBER)
                .build();
        invalidRequestLoginByPassportDto = RequestLoginDto.builder()
                .password("password1")
                .login("12345678901")
                .type(AuthenticationType.PASSPORT_NUMBER)
                .build();
        validFingerprintDto = FingerprintDto.builder()
                .fingerprint("666666")
                .build();
        invalidFingerprintDto = FingerprintDto.builder()
                .fingerprint("565656")
                .build();
    }

    @DisplayName("If client with given password and phone number exists then return access and refresh token")
    @Test
    void loginByPhone_shouldReturnAccessAndRefreshToken() throws Exception {
        mockMvc
                .perform(post(URL)
                        .content(JsonUtil.writeValue(validRequestLoginByPhoneDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If client with given password and passport number exists then return access and refresh token")
    @Test
    void loginByPassport_shouldReturnAccessAndRefreshToken() throws Exception {
        mockMvc
                .perform(post(URL)
                        .content(JsonUtil.writeValue(validRequestLoginByPassportDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If client with given password and passport number doesn't exist then return Http 422 status")
    @Test
    void loginByInvalidData_shouldReturn422() throws Exception {
        mockMvc
                .perform(post(URL)
                        .content(JsonUtil.writeValue(invalidRequestLoginByPassportDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @DisplayName("If client with uuid exists then we should save fingerprint")
    @Test
    void saveFingerprint_shouldSaveFingerprint() throws Exception {
        mockMvc
                .perform(post(URL+"/fingerprint")
                        .param("clientId", EXISTING_CLIENT_ID)
                        .content(JsonUtil.writeValue(validFingerprintDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If client with uuid doesn't exist then return 422 Http status")
    @Test
    void saveFingerprint_shouldReturnEntityNotFoundException() throws Exception {
        mockMvc
                .perform(post(URL+"/fingerprint")
                        .param("clientId", NOT_EXISTING_CLIENT_ID)
                        .content(JsonUtil.writeValue(invalidFingerprintDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @DisplayName("If client exists and fingerprint is valid then we should return access and refresh token")
    @Test
    void loginByPinWithValidData_shouldReturnAccessAndRefreshToken() throws Exception {
        MvcResult mvcAuthResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(validRequestLoginByPhoneDto))
        ).andReturn();
        String refreshToken = JsonPath.read(mvcAuthResult.getResponse().getContentAsString(),"$.refreshToken");

        mockMvc
                .perform(post(URL + "/pin")
                        .header("Authorization", "Bearer " + refreshToken)
                        .content(JsonUtil.writeValue(validFingerprintDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If fingerprint is invalid return 400 Http status")
    @Test
    void loginByPinWithInvalidFingerprint_shouldReturnInvalidPinException() throws Exception {
        MvcResult mvcAuthResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(validRequestLoginByPhoneDto))
        ).andReturn();
        String refreshToken = JsonPath.read(mvcAuthResult.getResponse().getContentAsString(),"$.refreshToken");

        mockMvc
                .perform(post(URL + "/pin")
                        .header("Authorization", "Bearer " + refreshToken)
                        .content(JsonUtil.writeValue(invalidFingerprintDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("If refresh token is invalid return 400 Http status")
    @Test
    void loginByPinWithInvalidRefreshToken_shouldReturnInvalidRefreshToken() throws Exception {
        String refreshToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI1ZjhjOGZiNC01MDNhLTQ3MjctYTQ3YS0xOWQ1Mzlm" +
                "YTY1N2MiLCJleHAiOjE2ODA3MDUzMTB9.0w0y9eOWOvYAwquPU79RkAE8uihl3DIZlS8wMpgrJhVh68jbJkJsrsMuEGDyGVnj";

        mockMvc
                .perform(post(URL + "/pin")
                        .header("Authorization", "Bearer " + refreshToken)
                        .content(JsonUtil.writeValue(invalidFingerprintDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("If user is authenticated then return new access token")
    void refreshAccessToken_shouldReturnNewAccessToken() throws Exception {
        MvcResult mvcAuthResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(validRequestLoginByPhoneDto))
        ).andReturn();
        String refreshToken = JsonPath.read(mvcAuthResult.getResponse().getContentAsString(),"$.refreshToken");

        MvcResult mvcResult = mockMvc.perform(get(URL+"/token")
                .header("Authorization", "Bearer " + refreshToken)
        ).andReturn();
        String newToken = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.accessToken");

        Assertions.assertNotNull(newToken);
    }

}
