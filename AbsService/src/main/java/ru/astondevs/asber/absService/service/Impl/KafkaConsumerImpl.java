package ru.astondevs.asber.absService.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.astondevs.asber.absService.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.absService.dto.DepositAgreementDto;
import ru.astondevs.asber.absService.dto.NewCardRequestDto;
import ru.astondevs.asber.absService.dto.NewCardResponseDto;
import ru.astondevs.asber.absService.service.generators.AgreementNumberGenerator;
import ru.astondevs.asber.absService.service.KafkaConsumer;
import ru.astondevs.asber.absService.service.KafkaProducer;
import ru.astondevs.asber.absService.service.generators.CardNumberGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {

    private final KafkaProducer kafkaProducer;

    private final AgreementNumberGenerator agreementNumberGenerator;

    private final CardNumberGenerator cardNumberGenerator;

    @Override
    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "deposit_to_master_new_deposit"
    )
    public void consumeDepositAgreementDto(DepositAgreementDto depositAgreementDto) {
        log.info("AbsService received DepositAgreementDto from Kafka:{}",
                depositAgreementDto.toString());

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(depositAgreementDto.getDurationMonths());
        String agreementNumber = agreementNumberGenerator.getNewAgreementNumber(startDate);
        Integer productId = depositAgreementDto.getProductId();
        BigDecimal initialAmount = depositAgreementDto.getInitialAmount();
        String cardNumber = depositAgreementDto.getCardNumber();
        Boolean autoRenewal = depositAgreementDto.getAutoRenewal();
        BigDecimal interestRate = depositAgreementDto.getInterestRate();

        AbsDepositAgreementMessageDto messageDto = new AbsDepositAgreementMessageDto(
                startDate, endDate, agreementNumber, productId, true,
                initialAmount, cardNumber, autoRenewal, interestRate
        );

       kafkaProducer.sendAbsDepositAgreementMessageDto(messageDto);
    }

    /**
     * Receives Dto that contains info to create {@link NewCardResponseDto}
     *
     * @param dto
     */
    @Override
    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "deposit_to_master_card_order"
    )
    public void consumeNewCardRequestDto(NewCardRequestDto dto) {
        NewCardResponseDto newCardResponseDto = new NewCardResponseDto();

        newCardResponseDto.setAccountNumber(dto.getAccountNumber());
        newCardResponseDto.setTransactionLimit(dto.getTransactionLimit());
        newCardResponseDto.setExpirationDate(dto.getExpirationDate());
        newCardResponseDto.setHolderName(dto.getHolderName());
        newCardResponseDto.setDigitalWallet(dto.getDigitalWallet());
        newCardResponseDto.setCardProductId(dto.getCardProductId());
        newCardResponseDto.setCardNumber(cardNumberGenerator.getNewCardNumber());
        newCardResponseDto.setIsDefault(true);

        kafkaProducer.sendNewCardResponseDto(newCardResponseDto);
    }
}
