package ru.astondevs.asber.userservice.controller;

import org.junit.jupiter.api.BeforeEach;
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
import ru.astondevs.asber.config.TestConfig;
import ru.astondevs.asber.userservice.ExternalSystemConfig;
import ru.astondevs.asber.userservice.dto.PasswordDto;
import ru.astondevs.asber.userservice.util.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class,
        classes = TestConfig.class)
@ActiveProfiles("test")
public class ResetPasswordControllerTest {

    private final String URL = "/api/v1/reset";
    private PasswordDto validPasswordDto;
    private String clientId = "798060ff-ed9b-4745-857c-a7a2f4a2e3a2";

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        validPasswordDto = PasswordDto.builder()
                .newPassword("testpassword")
                .build();
    }

    @DisplayName("If password and clientId is valid then change password and return status ok")
    @Test
    void changePasswordByClientId_shouldChangePassword() throws Exception {
        mockMvc.perform(patch(URL)
                        .param("clientId", clientId)
                        .content(JsonUtil.writeValue(validPasswordDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
