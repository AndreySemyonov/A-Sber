package ru.astondevs.asber.absService.service;

import ru.astondevs.asber.absService.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.absService.dto.DepositAgreementDto;
import ru.astondevs.asber.absService.dto.NewCardRequestDto;
import ru.astondevs.asber.absService.dto.NewCardResponseDto;

/**
 * Class that receives messages from Kafka.
 */
public interface KafkaConsumer {
    /**
     * Receives Dto that contains info to create {@link AbsDepositAgreementMessageDto}
     * @param depositAgreementDto is a source
     */
    void consumeDepositAgreementDto(DepositAgreementDto depositAgreementDto);

    /**
     * Receives Dto that contains info to create {@link NewCardResponseDto}
     * @param {@link NewCardRequestDto} is a source
     */
    void consumeNewCardRequestDto(NewCardRequestDto dto);
}
