package ru.astondevs.asber.depositservice.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.dto.NewCardRequestDto;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@Getter
public class KafkaConsumerStub {

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "deposit_to_master_new_deposit",
            containerFactory = "singleFactory"
    )
    public DepositAgreementDto consumeDepositAgreementDto(DepositAgreementDto depositAgreementDto) {
        log.info("KafkaConsumerStub received DepositAgreementDto from Kafka:{}",
                depositAgreementDto.toString());
        latch.countDown();

        return depositAgreementDto;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "deposit_to_master_card_order",
            containerFactory = "singleFactory"
    )
    public NewCardRequestDto consumeNewCardRequestDto(NewCardRequestDto dto) {
        log.info("KafkaConsumerStub received NewCardRequestDto from Kafka:{}",
                dto.toString());
        latch.countDown();

        return dto;
    }
}
