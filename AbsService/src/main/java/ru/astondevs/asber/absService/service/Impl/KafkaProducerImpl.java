package ru.astondevs.asber.absService.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.astondevs.asber.absService.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.absService.dto.NewCardResponseDto;
import ru.astondevs.asber.absService.service.KafkaProducer;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, AbsDepositAgreementMessageDto> absDepositAgreementMessageDtoKafkaTemplate;

    private final KafkaTemplate<String, NewCardResponseDto> newCardResponseDtoKafkaTemplate;

    @Override
    public void sendAbsDepositAgreementMessageDto(AbsDepositAgreementMessageDto message) {
        absDepositAgreementMessageDtoKafkaTemplate.send("master_to_deposit_new_deposit", message);
        log.info("AbsService sent message to Kafka: {}", message);
    }

    /**
     * Sends {@link NewCardResponseDto} to Kafka
     *
     * @param message is {@link NewCardResponseDto}
     */
    @Override
    public void sendNewCardResponseDto(NewCardResponseDto message) {
        newCardResponseDtoKafkaTemplate.send("master_to_deposit_card_order", message);
        log.info("AbsService sent message to Kafka: {}", message);
    }
}
