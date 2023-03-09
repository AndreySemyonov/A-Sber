package ru.astondevs.asber.userservice.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.astondevs.asber.config.TestConfig;
import ru.astondevs.asber.userservice.dto.PassportNumberDto;
import ru.astondevs.asber.userservice.dto.RegisterClientDto;
import ru.astondevs.asber.userservice.dto.PasswordDto;
import ru.astondevs.asber.userservice.ExternalSystemConfig;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;
import ru.astondevs.asber.userservice.util.JsonUtil;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class,
classes = TestConfig.class)
@ActiveProfiles("test")
class UserControllerTest {

    private final String URL = "/api/v1/user";
    private RegisterNotClientDto validDto;
    private RegisterClientDto registerClientDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        validDto = RegisterNotClientDto.builder()
                .mobilePhone("89211234567")
                .password("password")
                .securityQuestion("is it my security question?")
                .securityAnswer("probably yes")
                .email("not_already_existing_email@gmail.com")
                .firstName("Ivan")
                .middleName("Ivanov")
                .lastName("Ivanovich")
                .passportNumber("2949123456")
                .countryOfResidence("Russia")
                .build();

        registerClientDto = RegisterClientDto.builder()
                .id(UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2"))
                .mobilePhone("89111234567")
                .password("password")
                .securityQuestion("is it my security question?")
                .securityAnswer("probably yes")
                .email("some_user@gmail.com")
                .build();
    }

    @DisplayName("If all data is valid and client is not already registered then we register not client")
    @Test
    void registerNotClient_shouldRegisterNotClient() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL + "/new")
                                .content(JsonUtil.writeValue(validDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("If all data is valid and client is already registered then we add contacts and userProfile data")
    @Test
    void registerClient_shouldRegisterClient() throws Exception {
        WireMockServer wireMockServer = new WireMockServer(8082);
        wireMockServer.stubFor(WireMock.get(
                urlEqualTo("/api/v1/deposits-count?clientId=798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
                )
                .willReturn(aResponse()
                        .withBody("1")
                        .withHeader("Content-Type","application/json")
                        .withStatus(200)
        ));
        wireMockServer.start();
        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL)
                                .content(JsonUtil.writeValue(registerClientDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print());
        wireMockServer.stop();
    }

    @Test
    void getStatusNotClient_shouldCreateClientStatusRegistrationForNotClient() throws Exception {
        mockMvc
                .perform(get(URL + "/status")
                        .param("mobilePhone", validDto.getMobilePhone())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.clientStatus").value(ClientStatus.NOT_CLIENT.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If client with such a passportNumber already exists then return phone number")
    @Test
    void getPhoneByPassportNumber_shouldReturnPhone() throws Exception {
        mockMvc
                .perform(post(URL + "/phone")
                        .content("{\"passportNumber\":\"1234567890\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.mobilePhone").value("89111234567"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("If client with such a passportNumber doesn't exist then return 422 Http status")
    @Test
    void getPhoneByPassportNumber_withNotExistingPassportNumber_shouldReturn422() throws Exception {
        mockMvc
                .perform(post(URL + "/phone")
                        .content("{\"passportNumber\":\"1111111111\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}