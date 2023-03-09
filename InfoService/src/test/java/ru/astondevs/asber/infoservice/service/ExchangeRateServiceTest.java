package ru.astondevs.asber.infoservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;
import ru.astondevs.asber.infoservice.mapper.ExchangeRateMapper;
import ru.astondevs.asber.infoservice.repository.ExchangeRateRepository;
import ru.astondevs.asber.infoservice.service.impl.ExchangeRateServiceImpl;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private ExchangeRateMapper exchangeRateMapper;

    private ExchangeRate existingUsdExchangeRate;

    private ExchangeRate updatedUsdExchangeRate;

    private ExchangeRate createdUsdExchangeRate;

    private ExchangeRateDto newExchangeRateDto;

    private ExchangeRate newExchangeRate;

    @BeforeEach
    void setUp() {
        existingUsdExchangeRate = ExchangeRate.builder()
            .id(UUID.fromString("e2c6d6d1-d56f-43a6-a8f6-44c01cca9058"))
            .buyingRate(BigDecimal.valueOf(228.0))
            .sellingRate(BigDecimal.valueOf(322.0))
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        newExchangeRate = ExchangeRate.builder()
            .id(null)
            .buyingRate(BigDecimal.valueOf(100.0))
            .sellingRate(BigDecimal.valueOf(322.0))
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        newExchangeRateDto = ExchangeRateDto.builder()
            .buyingRate(BigDecimal.valueOf(100.0).toString())
            .sellingRate(BigDecimal.valueOf(322.0).toString())
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(LocalDateTime.now())
            .build();

        updatedUsdExchangeRate = ExchangeRate.builder()
            .id(UUID.fromString("e2c6d6d1-d56f-43a6-a8f6-44c01cca9058"))
            .buyingRate(BigDecimal.valueOf(100.0))
            .sellingRate(BigDecimal.valueOf(322.0))
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        createdUsdExchangeRate = ExchangeRate.builder()
            .id(UUID.randomUUID())
            .buyingRate(BigDecimal.valueOf(100.0))
            .sellingRate(BigDecimal.valueOf(322.0))
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(Timestamp.valueOf(LocalDateTime.now()))
            .isCross(true)
            .sign("sign")
            .isoCode("iso_code")
            .build();
    }

    @Test
    @DisplayName("if user wants to get exchange rate by currnecy code then return it")
    void getExchangeRateByCurrencyCode_shouldReturnExchangeRate() {
        when(exchangeRateRepository.findByCurrencyCode(any())).thenReturn(
            Optional.of(existingUsdExchangeRate));
        ExchangeRate resultExchangeRate = exchangeRateService.getExchangeRateByCurrencyCode(
            "123");
        assertNotNull(resultExchangeRate);
        assertEquals("Доллар США", resultExchangeRate.getName());

    }

    @Test
    @DisplayName("if user wants to get exchange rate by currnecy code then return it")
    void getExchangeRateByNonExistingCurrencyCode_shouldThrowEntityNotFoundException() {
        when(exchangeRateRepository.findByCurrencyCode(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
            () -> exchangeRateService.getExchangeRateByCurrencyCode("123")
        );

    }

    @Test
    @DisplayName("if user wants to update existing exchange rate then update it")
    void updateExchangeRateWithExchangeRateDto_shouldReturnUpdatedExchangeRate() {
        when(exchangeRateMapper.toExchangeRate(any())).thenReturn(newExchangeRate);

        when(exchangeRateRepository.findByCurrencyCode(any())).thenReturn(
            Optional.of(existingUsdExchangeRate));

        when(exchangeRateRepository.save(any())).thenReturn(updatedUsdExchangeRate);

        ExchangeRate updatedExchangeRate = exchangeRateService.updateOrCreateExchangeRate(
            newExchangeRateDto);

        assertNotNull(updatedExchangeRate);
        assertEquals("Доллар США", updatedExchangeRate.getName());
        assertEquals(BigDecimal.valueOf(100.0), updatedExchangeRate.getBuyingRate());
        assertEquals("USD", updatedExchangeRate.getCurrencyCode());
    }

    @Test
    @DisplayName("if user wants to update non existing exchange rate then create it")
    void createExchangeRateWithExchangeRateDto_shouldReturnCreatedExchangeRate() {
        when(exchangeRateMapper.toExchangeRate(any())).thenReturn(newExchangeRate);

        when(exchangeRateRepository.findByCurrencyCode(any())).thenReturn(
            Optional.empty());

        when(exchangeRateRepository.save(any())).thenReturn(createdUsdExchangeRate);

        ExchangeRate createdExchangeRate = exchangeRateService.updateOrCreateExchangeRate(
            newExchangeRateDto);

        assertNotNull(createdExchangeRate);
        assertEquals("Доллар США", createdExchangeRate.getName());
        assertEquals(BigDecimal.valueOf(100.0), createdExchangeRate.getBuyingRate());
        assertEquals("USD", createdExchangeRate.getCurrencyCode());
        assertEquals(true, createdExchangeRate.getIsCross());
        assertEquals("sign", createdExchangeRate.getSign());
        assertEquals("iso_code", createdExchangeRate.getIsoCode());
    }


}
