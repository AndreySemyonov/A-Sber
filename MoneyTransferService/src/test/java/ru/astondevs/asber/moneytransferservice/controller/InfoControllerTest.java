package ru.astondevs.asber.moneytransferservice.controller;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
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
import ru.astondevs.asber.moneytransferservice.dto.AccountNumberDto;
import ru.astondevs.asber.moneytransferservice.service.DepositServiceClient;

import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
public class InfoControllerTest {

    private final String URL = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepositServiceClient depositServiceClient;

    @Test
    @DisplayName("If user inputs card number then returns holder's account number")
    void checkCardNumber_shouldReturnAccountNumber() throws Exception {
        final String existingCardNumber = "408181057000012";
        final String moneyTransferUrl = String.format("%s/info/check-card", URL);

        when(depositServiceClient.isCardNumberValid(existingCardNumber)).thenReturn(new AccountNumberDto("accountnumber1"));

        mockMvc.perform(get(moneyTransferUrl)
                        .param("cardNumber", existingCardNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("accountnumber1"))
                .andDo(print());
    }

    @Test
    @DisplayName("If user inputs card number then returns 404")
    void checkCardNumber_shouldReturnNotFoundException() throws Exception {
        final String notExistingCardNumber = "408181057000019";
        final String moneyTransferUrl = String.format("%s/info/check-card", URL);

        when(depositServiceClient.isCardNumberValid(notExistingCardNumber))
                .thenThrow(new FeignException.NotFound(null,Request.create(Request.HttpMethod.GET,
                        "/api/v1/check-card",new HashMap<>(),null,null,new RequestTemplate()),
                        null,null));

        mockMvc.perform(get(moneyTransferUrl)
                        .param("cardNumber", notExistingCardNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    @DisplayName("If user inputs card number then returns 500")
    void checkCardNumber_shouldReturnRuntimeException() throws Exception {
        final String notExistingCardNumber = "408181057000019";
        final String moneyTransferUrl = String.format("%s/info/check-card", URL);

        when(depositServiceClient.isCardNumberValid(notExistingCardNumber)).thenThrow(new RuntimeException());

        mockMvc.perform(get(moneyTransferUrl)
                        .param("cardNumber", notExistingCardNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}
