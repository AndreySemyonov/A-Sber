package ru.astondevs.asber.depositservice.service;

import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;

/**
 * Class that receives messages from Kafka.
 */
public interface KafkaConsumer {
    /**
     * Receives Dto that contains info to create {@link Agreement}
     * @param message is a source Dto
     */
    void consumeAbsDepositAgreementMessageDto(AbsDepositAgreementMessageDto message);

    /**
     * Receives Dto that contains info to create {@link Card}
     * @param message is a source Dto
     */
    void consumeNewCardResponseDto(NewCardResponseDto message);
}
