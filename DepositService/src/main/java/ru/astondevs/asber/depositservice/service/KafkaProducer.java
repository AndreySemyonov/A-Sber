package ru.astondevs.asber.depositservice.service;

import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.dto.NewCardRequestDto;

public interface KafkaProducer {
    /**
     * Sends {@link DepositAgreementDto} to Kafka
     * @param depositAgreementDto is {@link DepositAgreementDto}
     */
    void sendDepositAgreementDto(DepositAgreementDto depositAgreementDto);

    /**
     * Sends {@link NewCardRequestDto} to Kafka
     * @param dto is {@link NewCardRequestDto}
     */
    void sendNewCardRequestDto(NewCardRequestDto dto);
}
