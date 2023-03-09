package ru.astondevs.asber.depositservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.repository.AgreementRepository;
import ru.astondevs.asber.depositservice.repository.CardRepository;
import ru.astondevs.asber.depositservice.service.AgreementService;
import ru.astondevs.asber.depositservice.service.CardService;
import ru.astondevs.asber.depositservice.service.KafkaConsumer;

/**
 * Class that receives messages from Kafka.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {
    private final AgreementRepository agreementRepository;
    private final AgreementService agreementService;

    private final CardRepository cardRepository;

    private final CardService cardService;

    /**
     * Receives Dto that contains info to create {@link Agreement}
     * @param message is source Dto
     */
    @Override
    @Transactional
    @KafkaListener(
            topics = "master_to_deposit_new_deposit",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "singleFactory"
    )
    public void consumeAbsDepositAgreementMessageDto(AbsDepositAgreementMessageDto message) {
        log.info("DepositService received message from Kafka: {}", message);
                agreementService.createAgreementFromAbs(message);
    }

    /**
     * Receives Dto that contains info to create {@link Card}
     * @param message is source Dto
     */
    @Override
    @Transactional
    @KafkaListener(
            topics = "master_to_deposit_card_order",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "singleFactory"
    )
    public void consumeNewCardResponseDto(NewCardResponseDto message) {
        log.info("DepositService received message from Kafka: {}", message);
                cardService.createCardFromAbs(message);
    }
}
