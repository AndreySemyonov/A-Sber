package ru.astondevs.asber.depositservice.controller;

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
import ru.astondevs.asber.depositservice.ExternalSystemConfig;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.service.KafkaProducer;
import ru.astondevs.asber.depositservice.util.JsonUtil;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {ExternalSystemConfig.PostgresInitializer.class,
ExternalSystemConfig.KafkaInitializer.class})
class AgreementControllerTest {
    private final String URL = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    private DepositAgreementDto agreementDto;
    private AccountNumberDto accountNumberDto;
    private KafkaProducer kafkaProducer;

    @BeforeAll
    public void setup() {
        agreementDto = DepositAgreementDto.builder()
                .cardNumber("408181057000012")
                .autoRenewal(true)
                .durationMonths(6)
                .initialAmount(BigDecimal.valueOf(100))
                .interestRate(BigDecimal.valueOf(6))
                .productId(1).build();
        accountNumberDto = AccountNumberDto.builder()
                .accountNumber("accountnumber1")
                .build();
    }

    @Test
    @DisplayName("If account founds by cardNumber then save new agreement and return status OK")
    void createNewDepositAgreement_shouldCreateNewDepositAgreement() throws Exception {
        final String createNewDepositAgreementUrl = String.format("%s/deposit-orders/new", URL);

        mockMvc.perform(post(createNewDepositAgreementUrl)
                        .content(JsonUtil.writeValue(agreementDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to cancel renewal status then cancel renewal status")
    void changeRenewalStatus_shouldChangeRenewalStatus() throws Exception {
        final String agreementId = "a965e7ee-5eae-11ed-9b6a-0242ac120002";
        final String changeRenewalStatusUrl = String.format("%s/deposits/%s/auto-renewal", URL, agreementId);
        final String clientId = "798060ff-ed9b-4745-857c-a7a2f4a2e3a2";

        mockMvc.perform(patch(changeRenewalStatusUrl)
                        .param("clientId", clientId)
                        .param("autoRenewal", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to revoke deposit then deposit status should change")
    void revokeDeposit_shouldChangeDepositStatus() throws Exception {
        final String agreementId = "a965e7ee-5eae-11ed-9b6a-0242ac120002";
        final String revokeDepositUrl = String.format("%s/deposits/%s/revocation", URL, agreementId);

        mockMvc.perform(patch(revokeDepositUrl)
                        .content(JsonUtil.writeValue(accountNumberDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}