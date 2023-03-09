package ru.astondevs.asber.depositservice.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;

@RequiredArgsConstructor
@Getter
@Slf4j
@Component
public class KafkaProducerStub {
    private final KafkaTemplate<String, AbsDepositAgreementMessageDto> absDepositAgreementMessageDtoKafkaTemplate;

    private final KafkaTemplate<String, NewCardResponseDto> newCardResponseDtoKafkaTemplate;

    public void sendDepositAgreementDto(AbsDepositAgreementMessageDto dto) {
        absDepositAgreementMessageDtoKafkaTemplate.send("master_to_deposit_new_deposit", dto);
        log.info("Message to Kafka was sent");
    }

    public void sendNewCardResponseDto(NewCardResponseDto dto) {
        newCardResponseDtoKafkaTemplate.send("master_to_deposit_card_order", dto);
        log.info("Message to Kafka was sent");
    }

}
