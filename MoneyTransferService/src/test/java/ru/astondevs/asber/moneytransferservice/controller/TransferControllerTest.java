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
import ru.astondevs.asber.moneytransferservice.dto.TransferRequestDto;
import ru.astondevs.asber.moneytransferservice.util.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
public class TransferControllerTest {

    private final String URL = "/api/v1/payments";

    @Autowired
    private MockMvc mockMvc;

    private TransferRequestDto newTransferDto;
    @BeforeEach
    void setUp() {
        newTransferDto = TransferRequestDto.builder()
            .cardNumber("798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
            .transferTypeId(1111111L)
            .sum(BigDecimal.valueOf(1000))
            .remitterCardNumber("408181057000012")
            .name("nothing")
            .payeeAccountNumber("account1")
            .payeeCardNumber("408181057000014")
            .mobilePhone(null)
            .sumCommission(BigDecimal.ONE)
            .isFavourite(true)
            .periodicity(12)
            .startDate(LocalDate.of(2022,2,2))
            .purpose("nothing")
            .currencyExchange(BigDecimal.valueOf(60))
            .build();
    }

    @Test
    @DisplayName("If transfer type founds then save new transfer and return TransferResponseDto")
    void newTransfer_shouldCreateAndReturnTransferResponseDto() throws Exception {
        final String createNewTransferUrl = String.format("%s/new", URL);
        final String clientId = "d3db02f6-5eb9-11ed-9b6a-0242ac120002";

        mockMvc.perform(post(createNewTransferUrl)
                        .param("clientId", clientId)
                        .content(JsonUtil.writeValue(newTransferDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user deletes drafted transfer then gets response OK")
    void changeTransferStatusToDeleted_shouldReturnOk() throws Exception {
        final String transferId = "5555555";
        final String createChangeTransferStatusUrl = String.format("%s/%s/draft", URL, transferId);

        mockMvc.perform(delete(createChangeTransferStatusUrl))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user deletes transfer from favorites then get response OK")
    void changeIsFavouriteStatus_shouldReturnOk() throws Exception {
        final String transferId = "1111111";
        final String createChangeIsFavouriteStatusUrl = String.format("%s/%s/favorites", URL, transferId);

        mockMvc.perform(patch(createChangeIsFavouriteStatusUrl)
                        .param("isFavourite", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isFavourite").value(false))
                .andDo(print());
    }
}
