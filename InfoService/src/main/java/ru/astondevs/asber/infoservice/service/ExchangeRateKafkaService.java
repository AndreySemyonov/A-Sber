package ru.astondevs.asber.infoservice.service;

import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;

/**
 * Interface of exchange rate kafka service.
 */
public interface ExchangeRateKafkaService {

    void send(ExchangeRateDto exchangeRateDto);

    void consume(ExchangeRateDto exchangeRateDto);

}
