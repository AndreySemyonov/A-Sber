package ru.astondevs.asber.moneytransferservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import ru.astondevs.asber.moneytransferservice.dto.TransferStatusDto;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;
import ru.astondevs.asber.moneytransferservice.util.JsonUtil;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class TransferDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("If user wants to update existing transfer status then update it")
    void updateTransferStatusToInProgress() throws Exception {
        final Long transferId = 1111111L;
        final String url = String.format("/payments/%d/status", transferId);
        final TransferStatusDto dto = TransferStatusDto.builder()
            .transferId(transferId.toString())
            .status(TransferStatus.IN_PROGRESS)
            .build();

        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
            .andDo(print());
    }

    @Test
    @DisplayName("If user wants to update non existing transfer status then return 404 error")
    void updateStatusOfNonExistingTransfer() throws Exception {
        final Long transferId = 12346456L;
        final String url = String.format("/payments/%d/status", transferId);
        final TransferStatusDto dto = TransferStatusDto.builder()
            .transferId(transferId.toString())
            .status(TransferStatus.IN_PROGRESS)
            .build();

        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dto)))
            .andExpect(status().isNotFound())
            .andDo(print());
    }
}