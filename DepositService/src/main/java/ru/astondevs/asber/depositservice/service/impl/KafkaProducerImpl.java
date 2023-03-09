package ru.astondevs.asber.depositservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.dto.NewCardRequestDto;
import ru.astondevs.asber.depositservice.service.KafkaProducer;


/**
 * Class that sends messages to Kafka
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, DepositAgreementDto> depositAgreementDtoKafkaTemplate;

    private final KafkaTemplate<String, NewCardRequestDto> newCardRequestDtoKafkaTemplate;

    @Override
    public void sendDepositAgreementDto(DepositAgreementDto depositAgreementDto) {
        depositAgreementDtoKafkaTemplate.send("deposit_to_master_new_deposit", depositAgreementDto);
        log.info("DepositAgreementDto to Kafka was sent: {}", depositAgreementDto);
    }

    /**
     * Sends {@link NewCardRequestDto} to Kafka
     *
     * @param dto is {@link NewCardRequestDto}
     */
    @Override
    public void sendNewCardRequestDto(NewCardRequestDto dto) {
        newCardRequestDtoKafkaTemplate.send("deposit_to_master_card_order", dto);
        log.info("NewCardRequestDto to Kafka was sent: {}", dto);
    }
}
