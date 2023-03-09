package ru.astondevs.asber.absService.service;

import ru.astondevs.asber.absService.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.absService.dto.NewCardResponseDto;

/**
 * Class that sends messages to Kafka
 */
public interface KafkaProducer {
    /**
     * Sends {@link AbsDepositAgreementMessageDto} to Kafka
     * @param message is {@link AbsDepositAgreementMessageDto}
     */
    void sendAbsDepositAgreementMessageDto(AbsDepositAgreementMessageDto message);

    /**
     * Sends {@link NewCardResponseDto} to Kafka
     * @param message is {@link NewCardResponseDto}
     */
    void sendNewCardResponseDto(NewCardResponseDto message);
}
