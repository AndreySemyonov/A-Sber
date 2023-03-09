package ru.astondevs.asber.infoservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;
import ru.astondevs.asber.infoservice.service.ExchangeRateKafkaService;
import ru.astondevs.asber.infoservice.service.ExchangeRateService;

/**
 * Exchange rate kafka service for USD.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateKafkaServiceImpl implements ExchangeRateKafkaService {

    private final KafkaTemplate<String, ExchangeRateDto> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final ExchangeRateService exchangeRateService;

    /**
     * Exchange rate producer
     */
    @Override
    public void send(ExchangeRateDto exchangeRateDto) {
        kafkaTemplate.send("rates", exchangeRateDto);
    }

    /**
     * USD exchange rate consumer
     */
    @Override
    @KafkaListener(
        id = "Usd",
        topics = {"rates"},
        containerFactory = "singleFactory",
        groupId = "CurrencyConsumers1")
    public void consume(ExchangeRateDto exchangeRateDto) {
        log.info("=> consumed {}", writeValueAsString(exchangeRateDto));
        ExchangeRate updatedOrCreatedExchangeRate = exchangeRateService.updateOrCreateExchangeRate(
            exchangeRateDto);
        log.info("Updated or created exchange rate with id {}",
            updatedOrCreatedExchangeRate.getId());
    }

    /**
     * Method that converts {@link ExchangeRateDto} into json.
     *
     * @param exchangeRateDto - {@link ExchangeRateDto} of {@link ExchangeRate}
     * @return {@link ExchangeRateDto} as json
     */
    private String writeValueAsString(ExchangeRateDto exchangeRateDto) {
        try {
            return objectMapper.writeValueAsString(exchangeRateDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(
                "Writing value to JSON failed: " + exchangeRateDto.toString());
        }
    }
}
