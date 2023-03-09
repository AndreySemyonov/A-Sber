package ru.astondevs.asber.moneytransferservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.astondevs.asber.moneytransferservice.ExternalSystemConfig;
import ru.astondevs.asber.moneytransferservice.dto.ExchangeRateDto;
import ru.astondevs.asber.moneytransferservice.service.InfoServiceClient;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
public class ExchangeRateControllerTest {

    private final String URL = "/auth";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InfoServiceClient infoServiceClient;

    @Test
    @DisplayName("If user inputs existing currency code then returns currency code data")
    void checkCurrencyCode_shouldReturnCurrencyCodeData() throws Exception {
        final String existingCurrencyCode = "USD";
        Map<String, String> existingCurrencyCodeJson = new HashMap<>();
        existingCurrencyCodeJson.put("currencyCode", existingCurrencyCode);
        final String infoServiceUrl = String.format("%s/rates", URL);

        when(infoServiceClient.getExchangeRate(existingCurrencyCodeJson))
                .thenReturn(new ExchangeRateDto("228.0000", "322.0000", "USD", 1));

        mockMvc.perform(get(infoServiceUrl)
                        .param("currencyCodeFrom", existingCurrencyCode)
                        .param("currencyCodeTo", existingCurrencyCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sellingRate").value("322.0000"))
                .andDo(print());
    }

    @Test
    @DisplayName("If user inputs non existing currency code then returns 500")
    void checkCurrencyCode_shouldReturnInfoServiceRequestException() throws Exception {
        final String nonExistingCurrencyCode = "USD1";
        Map<String, String> nonExistingCurrencyCodeJson = new HashMap<>();
        nonExistingCurrencyCodeJson.put("currencyCode", nonExistingCurrencyCode);
        final String infoServiceUrl = String.format("%s/rates", URL);

        when(infoServiceClient.getExchangeRate(nonExistingCurrencyCodeJson))
                .thenReturn(new ExchangeRateDto());

        mockMvc.perform(get(infoServiceUrl)
                        .param("currencyCodeFrom", nonExistingCurrencyCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}
