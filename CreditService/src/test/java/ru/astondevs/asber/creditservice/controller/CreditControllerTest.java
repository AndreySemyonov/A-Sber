package ru.astondevs.asber.creditservice.controller;

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
import ru.astondevs.asber.creditservice.ExternalSystemConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
public class CreditControllerTest {

    private final String URL = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("If user wants to get credit payment schedule by CreditId then return it")
    void getCreditPaymentScheduleByCreditId_shouldReturnCreditPaymentScheduleDto()  throws Exception {
        final String existingCreditUUID = "9093905a-6e6b-11ed-a1eb-0242ac120001";
        final String creditScheduleUrl = String.format("%s/credits/%s/schedule", URL, existingCreditUUID);

        mockMvc.perform(get(creditScheduleUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("account_number1"))
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to get credit payment schedule by Non existing CreditId then return error")
    void getCreditPaymentScheduleByNonExistingCreditId_shouldReturnHttpCode4xx() throws Exception {
        final String nonExistingCreditUUID = "8093905a-6e6b-11ed-a1eb-0242ac120001";
        final String creditScheduleUrl = String.format("%s/credits/%s/schedule", URL, nonExistingCreditUUID);

        mockMvc.perform(get(creditScheduleUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to find his credits then return credits")
    void getClientCredits_shouldReturnCredits() throws Exception {
        final String getClientCreditsUrl = String.format("%s/credits", URL);
        final String сlientId = "798060ff-ed9b-4745-857c-a7a2f4a2e3a2";

        mockMvc.perform(get(getClientCreditsUrl)
                        .queryParam("clientId", сlientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to find credit then return credit")
    void getCredit_shouldReturnCredit() throws Exception {
        final String creditId = "9093905a-6e6b-11ed-a1eb-0242ac120001";
        final String getCreditUrl = String.format("%s/credits/%s", URL, creditId);
        final String сlientId = "798060ff-ed9b-4745-857c-a7a2f4a2e3a2";

        mockMvc.perform(get(getCreditUrl)
                        .queryParam("clientId", сlientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user has active accounts then return count of them")
    void getCreditsCount_shouldReturnActiveAccountsCount() throws Exception {
        final String getCreditsCountUrl = String.format("%s/credits/credits-count", URL);
        final String сlientId = "798060ff-ed9b-4745-857c-a7a2f4a2e3a2";

        mockMvc.perform(get(getCreditsCountUrl)
                        .queryParam("clientId", сlientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}