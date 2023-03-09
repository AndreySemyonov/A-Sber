package ru.astondevs.asber.depositservice.controller;

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
import ru.astondevs.asber.depositservice.ExternalSystemConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext
@ContextConfiguration(initializers = {ExternalSystemConfig.PostgresInitializer.class,
        ExternalSystemConfig.KafkaInitializer.class})
public class DepositControllerTest {
    private final String URL = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("If accounts with clientId are found then return ClientDepositsResponseDto")
    void getDeposits_shouldReturnClientDepositsResponseDto() throws Exception {
        final String getDepositsUrl = String.format("%s/deposits", URL);
        final String existClientId = "5f8c8fb4-503a-4727-a47a-19d539fa657c";

        mockMvc.perform(get(getDepositsUrl)
                        .param("clientId", existClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If account with agreementId, clientId and cardId is found then return ClientDepositsResponseDto")
    void getDeposit_shouldReturnClientDepositResponseDto() throws Exception {
        final String agreementId = "b289e118-5eae-11ed-9b6a-0242ac120002";
        final String getDepositUrl = String.format("%s/deposits/%s", URL, agreementId);
        final String existClientId = "5f8c8fb4-503a-4727-a47a-19d539fa657c";
        final String cardId = "d3db02f6-5eb9-11ed-9b6a-0242ac120002";

        mockMvc.perform(get(getDepositUrl)
                    .param("agreementId", agreementId)
                    .param("clientId", existClientId)
                    .param("cardId", cardId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentBalance").value("40100.0"))
                .andDo(print());
    }

    @Test
    @DisplayName("If accounts with clientId are found then return ClientCardsResponseDto")
    void getClientCards_shouldReturnClientCardsResponseDto() throws Exception {
        final String getCardProductsUrl = String.format("%s/deposit-card", URL);
        final String existClientId = "5f8c8fb4-503a-4727-a47a-19d539fa657c";

        mockMvc.perform(get(getCardProductsUrl)
                        .param("clientId", existClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to get all accounts then return all accounts by clientid")
    void getAccounts_shouldReturnListOfAccounts() throws Exception {
        final String getAccountsUrl = String.format("%s/accounts", URL);
        final String existClientId = "5f8c8fb4-503a-4727-a47a-19d539fa657c";

        mockMvc.perform(get(getAccountsUrl)
                        .param("clientId", existClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].accountNumber").value("accountnumber3"))
                .andDo(print());
    }

    @Test
    @DisplayName("If user has active accounts then return count of them")
    void getDepositsCount_shouldReturnActiveAccountsCount() throws Exception {
        final String getDepositsCountUrl = String.format("%s/deposits-count", URL);
        final String clientId = "798060ff-ed9b-4745-857c-a7a2f4a2e3a2";

        mockMvc.perform(get(getDepositsCountUrl)
                        .queryParam("clientId", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
