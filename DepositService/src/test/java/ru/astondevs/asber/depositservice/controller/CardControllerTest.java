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
import ru.astondevs.asber.depositservice.dto.CardStatus;
import ru.astondevs.asber.depositservice.dto.CardStatusDto;
import ru.astondevs.asber.depositservice.dto.DigitalWallet;
import ru.astondevs.asber.depositservice.dto.NewCardRequestDto;
import ru.astondevs.asber.depositservice.util.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class CardControllerTest {

    private final String URL = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    private CardStatusDto cardStatusDto;
    private NewCardRequestDto newCardDto;

    @BeforeAll
    public void setup() {
        cardStatusDto = CardStatusDto.builder()
                .cardNumber("408181057000014")
                .status(CardStatus.BLOCKED)
                .build();
        newCardDto = NewCardRequestDto.builder()
                .accountNumber("accountnumber1")
                .transactionLimit(BigDecimal.valueOf(1000000.00))
                .expirationDate(LocalDate.now())
                .holderName("Ivan Ivanov")
                .digitalWallet(DigitalWallet.MIRPAY)
                .cardProductId(1)
                .build();
    }

    @Test
    @DisplayName("If client requests to change card status then change status")
    void changeCardStatus_shouldChangeCardStatus() throws Exception {
        final String changeCardStatusUrl = String.format("%s/change-card-status", URL);
        final String cardId = "d3db02f6-5eb9-11ed-9b6a-0242ac120002";

        mockMvc.perform(patch(changeCardStatusUrl)
                        .param("cardId", cardId)
                        .content(JsonUtil.writeValue(cardStatusDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If account and  card_product are found then save new card")
    void createNewCard_shouldCreateAndReturnNewCardResponseDto() throws Exception {
        final String createNewCardUrl = String.format("%s/deposit-card-orders/new", URL);

        mockMvc.perform(post(createNewCardUrl)
                        .content(JsonUtil.writeValue(newCardDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If card number is found then return AccountNumberDto")
    void getAccountNumber_shouldReturnAccountNumberDto() throws Exception {
        final String getAccountNumberUrl = String.format("%s/account-number", URL);
        final String cardNumber = "408181057000013";

        mockMvc.perform(get(getAccountNumberUrl)
                        .param("cardNumber", cardNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("accountnumber2"))
                .andDo(print());
    }
}