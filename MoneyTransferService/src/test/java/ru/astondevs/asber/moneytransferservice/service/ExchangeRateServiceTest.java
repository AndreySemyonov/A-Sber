package ru.astondevs.asber.moneytransferservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.moneytransferservice.dto.ExchangeRateDto;
import ru.astondevs.asber.moneytransferservice.service.impl.ExchangeRateServiceImpl;
import ru.astondevs.asber.moneytransferservice.util.exception.InfoServiceRequestException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private InfoServiceClient infoServiceClient;

    private ExchangeRateDto exchangeRateDto;

    private String currencyCode;
    private String nonExistingCurrencyCode;

    @BeforeEach
    void setUp() {
        exchangeRateDto = ExchangeRateDto.builder()
                .buyingRate("228.0000")
                .sellingRate("322.0000")
                .currencyCode("USD")
                .unit(1).build();
        currencyCode = "USD";
        nonExistingCurrencyCode = "USD1";
    }

    @Test
    @DisplayName("If exchange rate DTO found by currency codes and calculated then return ExchangeRateDto")
    void getExchangeRate_shouldReturnExchangeRateDto() {
        Map<String, String> currencyCodeJson = new HashMap<>();
        currencyCodeJson.put("currencyCode", currencyCode);
        when(infoServiceClient.getExchangeRate(currencyCodeJson)).thenReturn(exchangeRateDto);

        ExchangeRateDto result = exchangeRateService.getExchangeRate(currencyCode, currencyCode);
        assertEquals("0.7080745341614907", result.getBuyingRate());
    }

    @Test
    @DisplayName("If exchange rate DTO did not found by currency codes and did not  calculated " +
            "then return ExchangeRateDto")
    void getExchangeRate_shouldReturnEntityNotFountException() {
        Map<String, String> currencyCodeJson = new HashMap<>();
        currencyCodeJson.put("currencyCode", nonExistingCurrencyCode);
        when(infoServiceClient.getExchangeRate(currencyCodeJson)).thenReturn(exchangeRateDto);

        assertThrows(InfoServiceRequestException.class,
                () -> exchangeRateService.getExchangeRate(currencyCode, currencyCode));
    }
}
