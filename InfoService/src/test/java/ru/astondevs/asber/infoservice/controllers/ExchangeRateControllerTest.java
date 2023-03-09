package ru.astondevs.asber.infoservice.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
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
import ru.astondevs.asber.infoservice.ExternalSystemConfig;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String EXCHANGE_RATE_URL = "/exchange-rates";

    @Test
    @DisplayName("If user wants to get exchange rate for usd by currency code then return it")
    void getExchangeRateForUSD_shouldReturnExchangeRateDto() throws Exception {

        Map<String, String> currencyCode = new HashMap<>();
        currencyCode.put("currencyCode", "USD");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(EXCHANGE_RATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currencyCode)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Доллар США"))
            .andDo(print());
    }

    @Test
    @DisplayName("If user wants to get exchange rate for non existing currency then expect 422 error")
    void getExchangeRateForNonExistingCurrency_shouldReturn422Error() throws Exception {
        Map<String, String> currencyCode = new HashMap<>();
        currencyCode.put("currencyCode", "123");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(EXCHANGE_RATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currencyCode)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(print());
    }

    @Test
    @DisplayName("If user wants to get all exchange rates then return list of them")
    void getAllExchangeRates_shouldReturnListOfExchangeRates() throws Exception {
        mockMvc.perform(get(EXCHANGE_RATE_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andDo(print());
    }

}