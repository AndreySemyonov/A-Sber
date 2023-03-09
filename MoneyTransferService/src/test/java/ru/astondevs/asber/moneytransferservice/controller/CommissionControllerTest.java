package ru.astondevs.asber.moneytransferservice.controller;

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
import ru.astondevs.asber.moneytransferservice.ExternalSystemConfig;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
public class CommissionControllerTest {

    private final String URL = "/api/v1/commission";

    @Autowired
    private MockMvc mockMvc;

    private TransferType transferType;

    @BeforeEach
    void setUp() {
        transferType = TransferType.builder()
                .transferTypeId(1111111L)
                .transferTypeName("card")
                .maxTransferSum(BigDecimal.valueOf(1000000.0000))
                .minTransferSum(BigDecimal.ZERO)
                .commissionPercent(BigDecimal.valueOf(5.0))
                .commissionFix(null)
                .maxCommission(10)
                .minCommission(1)
                .currencyFrom(CurrencyCode.RUB).build();
    }

    @Test
    @DisplayName("User sends request with correct params then gets correct response")
    void getCommissionResponse_shouldReturnTransferTypeResponseDto() throws Exception {

        mockMvc.perform(get(URL)
                        .param("typeName", "card")
                        .param("currencyCode", "RUB")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commissionFix").value(transferType.getCommissionFix()))
                .andExpect(jsonPath("$.commissionPercent").value(transferType.getCommissionPercent()))
                .andDo(print());
    }
}
