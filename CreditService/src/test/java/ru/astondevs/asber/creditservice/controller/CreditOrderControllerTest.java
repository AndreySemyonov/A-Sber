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
import ru.astondevs.asber.creditservice.dto.CreditOrderResponseDto;
import ru.astondevs.asber.creditservice.dto.NewCreditOrderRequestDto;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;
import ru.astondevs.asber.creditservice.util.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
public class CreditOrderControllerTest {

    private final String URL = "/api/v1/credit-orders";

    private CreditOrderResponseDto creditOrderResponseDto;

    @Autowired
    private MockMvc mockMvc;

    private NewCreditOrderRequestDto newCreditOrderRequestDto;

    @BeforeEach
    void setUp() {
        creditOrderResponseDto = CreditOrderResponseDto.builder()
                .id(UUID.fromString("8a5cbaa4-6e6b-11ed-a1eb-0242ac120002"))
                .productId(3)
                .status(CreditStatus.ACTIVE)
                .amount(BigDecimal.valueOf(200000.0000))
                .periodMonths(6)
                .creationDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("If user wants to get his credit orders and clientId is valid then return CreditOrderResponseDto")
    void getCreditOrders_shouldReturnCreditOrderResponseDto() throws Exception {
        final String сlientId = "695aecfe-152f-421d-9c4b-5831b812cd2a";

        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", сlientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].productId").value(creditOrderResponseDto.getProductId()))
                .andExpect(jsonPath("$.[0].status").value(creditOrderResponseDto.getStatus().name()))
                .andExpect(jsonPath("$.[0].amount").value(creditOrderResponseDto.getAmount()))
                .andExpect(jsonPath("$.[0].periodMonths").value(creditOrderResponseDto.getPeriodMonths()))
                .andExpect(jsonPath("$.[0].creationDate").value(creditOrderResponseDto.getCreationDate().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to create a new credit order")
    void createNewCreditOrder_shouldCreateAndReturnNewCreditOrderRequestDto() throws Exception {
        final String creditOrderUrl = String.format("%s/new", URL);
        final String clientId = "3f28eed1-09e4-4cb4-81ec-3259e94d8b61";
        newCreditOrderRequestDto = NewCreditOrderRequestDto.builder()
                .productId(1)
                .amount(1)
                .periodMonths(3)
                .creationDate("16.01.2023")
                .monthlyIncome(2)
                .monthlyExpenditure(4)
                .employerIdentificationNumber("")
                .number("42")
                .build();

        mockMvc.perform(post(creditOrderUrl)
                        .content(JsonUtil.writeValue(newCreditOrderRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", clientId)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("If user wants to create a new credit order with wrong productId")
    void createNewCreditOrder_shouldReturnEntityNotFound() throws Exception {
        final String creditOrderUrl = String.format("%s/new", URL);
        final String clientId = "3f28eed1-09e4-4cb4-81ec-3259e94d8b61";
        newCreditOrderRequestDto = NewCreditOrderRequestDto.builder()
                .productId(999)
                .amount(1)
                .periodMonths(3)
                .creationDate("16.01.2023")
                .monthlyIncome(2)
                .monthlyExpenditure(4)
                .employerIdentificationNumber("")
                .number("42")
                .build();

        mockMvc.perform(post(creditOrderUrl)
                        .content(JsonUtil.writeValue(newCreditOrderRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", clientId)
                )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
