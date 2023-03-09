package ru.astondevs.asber.infoservice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;
import ru.astondevs.asber.infoservice.mapper.utils.ExchangeRateMapperHelper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ExchangeRateMapperImpl.class, ExchangeRateMapperHelper.class})
class ExchangeRateMapperTest {

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @Test
    @DisplayName("Convert ExchangeRateDto to ExchangeRate")
    void toExchangeRate() {
        ExchangeRateDto exchangeRateDto = ExchangeRateDto.builder()
            .buyingRate(BigDecimal.valueOf(228.0).toString())
            .sellingRate(BigDecimal.valueOf(322.0).toString())
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(LocalDateTime.now())
            .build();

        ExchangeRate exchangeRate = exchangeRateMapper.toExchangeRate(exchangeRateDto);

        assertNotNull(exchangeRate);
        assertEquals(BigDecimal.valueOf(228.0), exchangeRate.getBuyingRate());
        assertEquals(BigDecimal.valueOf(322.0), exchangeRate.getSellingRate());
        assertEquals("USD", exchangeRate.getCurrencyCode());
        assertEquals("Доллар США", exchangeRate.getName());
    }

    @Test
    @DisplayName("Convert null to ExchangeRate")
    void nullToExchangeRate() {
        ExchangeRate exchangeRate = exchangeRateMapper.toExchangeRate(null);
        assertNull(exchangeRate);
    }

    @Test
    @DisplayName("Convert ExchangeRate to ExchangeRateDto")
    void toExchangeRateDto() {
        ExchangeRate usdExchangeRate = ExchangeRate.builder()
            .id(UUID.fromString("e2c6d6d1-d56f-43a6-a8f6-44c01cca9058"))
            .buyingRate(BigDecimal.valueOf(228.0))
            .sellingRate(BigDecimal.valueOf(322.0))
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        ExchangeRateDto exchangeRateDto = exchangeRateMapper.toExchangeRateDto(usdExchangeRate);

        assertNotNull(exchangeRateDto);
        assertEquals(BigDecimal.valueOf(228.0).toString(), exchangeRateDto.getBuyingRate());
        assertEquals(BigDecimal.valueOf(322.0).toString(), exchangeRateDto.getSellingRate());
        assertEquals("USD", exchangeRateDto.getCurrencyCode());
        assertEquals("Доллар США", exchangeRateDto.getName());
    }

    @Test
    @DisplayName("Convert null to ExchangeRateDto")
    void nullToExchangeRateDto() {
        ExchangeRateDto exchangeRateDto = exchangeRateMapper.toExchangeRateDto(null);
        assertNull(exchangeRateDto);
    }
    @Test
    @DisplayName("Convert list of exchangeRate to list of exchangeRateDto")
    void listOfExchangeRatesToListOfExchangeRatesDto(){
        ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
        ExchangeRate exchangeRate1 = ExchangeRate.builder()
            .id(UUID.randomUUID())
            .buyingRate(BigDecimal.valueOf(228.0))
            .sellingRate(BigDecimal.valueOf(322.0))
            .currencyCode("USD")
            .unit(1)
            .name("Доллар США")
            .updateAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        ExchangeRate exchangeRate2 = ExchangeRate.builder()
            .id(UUID.randomUUID())
            .buyingRate(BigDecimal.valueOf(111))
            .sellingRate(BigDecimal.valueOf(32122.0))
            .currencyCode("ERU")
            .unit(1)
            .name("Доллар США")
            .updateAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        exchangeRates.add(exchangeRate1);
        exchangeRates.add(exchangeRate2);

        List<ExchangeRateDto> exchangeRateDtos = exchangeRateMapper.toExchangeRates(exchangeRates);
        assertEquals(2, exchangeRateDtos.size());
    }
    @Test
    @DisplayName("Convert null to exchangeRateDto list")
    void nullToListOfExchangeRatesDto(){
        List<ExchangeRateDto> exchangeRateDtos = exchangeRateMapper.toExchangeRates(null);
        assertNull(exchangeRateDtos);
    }
}