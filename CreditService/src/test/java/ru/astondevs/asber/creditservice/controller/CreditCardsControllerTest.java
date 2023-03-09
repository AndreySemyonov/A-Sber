package ru.astondevs.asber.creditservice.controller;

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
import ru.astondevs.asber.creditservice.ExternalSystemConfig;
import ru.astondevs.asber.creditservice.dto.CreditCardStatusDto;
import ru.astondevs.asber.creditservice.entity.enums.CardStatus;
import ru.astondevs.asber.creditservice.util.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
public class CreditCardsControllerTest {

    private final String URL = "/api/v1";

    private CreditCardStatusDto cardStatusDto;

    @BeforeEach
    void setUp() {
        cardStatusDto = CreditCardStatusDto.builder()
                .cardNumber("408181057000002")
                .cardStatus(CardStatus.BLOCKED)
                .build();
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("If user wants to find his cards then return cards")
    void getCardsByClientId_shouldReturnCards() throws Exception {
        final String existClientId = "695aecfe-152f-421d-9c4b-5831b812cd2a";
        final String creditScheduleUrl = String.format("%s/credit-cards/", URL);

        mockMvc.perform(get(creditScheduleUrl)
                        .param("clientId", existClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].cardNumber").value("408181057000002"))
                .andDo(print());
    }

    @Test
    @DisplayName("If client requests to change card status then change status")
    void changeCardStatus_shouldChangeCardStatus() throws Exception {
        final String changeCardStatusUrl = String.format("%s/credit-cards/active-cards", URL);

        mockMvc.perform(patch(changeCardStatusUrl)
                        .content(JsonUtil.writeValue(cardStatusDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to find his card by cardId and cardId is valid then return card")
    void getCardByCardId_shouldReturnCard() throws Exception {
        final String existingCardId = "46561a3e-6e80-11ed-a1eb-0242ac120001";
        final String cardByCardIdUrl = String.format("%s/credit-cards/%s", URL, existingCardId);

        mockMvc.perform(get(cardByCardIdUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("account_number1"))
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to find his card by cardId and cardId is notvalid then return 422")
    void getCardByCardId_shouldReturn422() throws Exception {
        final String notExistingCardId = "56561a3e-6e80-11ed-a1eb-0242ac120001";
        final String cardByCardIdUrl = String.format("%s/credit-cards/%s", URL, notExistingCardId);

        mockMvc.perform(get(cardByCardIdUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
