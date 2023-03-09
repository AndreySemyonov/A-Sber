package ru.astondevs.asber.depositservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.astondevs.asber.depositservice.ExternalSystemConfig;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.dto.DigitalWallet;
import ru.astondevs.asber.depositservice.dto.NewCardRequestDto;
import ru.astondevs.asber.depositservice.util.KafkaConsumerStub;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@ContextConfiguration(initializers = {ExternalSystemConfig.KafkaInitializer.class,
ExternalSystemConfig.PostgresInitializer.class})
@DirtiesContext
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaProducerTest {
    @Autowired
    private KafkaConsumerStub kafkaConsumerStub;
    @Autowired
    private KafkaProducer kafkaProducer;
    private DepositAgreementDto depositAgreementDto;
    private NewCardRequestDto newCardDto;

    @BeforeEach
    void setup() {
        depositAgreementDto = DepositAgreementDto.builder()
                .cardNumber("408181057000012")
                .autoRenewal(true)
                .durationMonths(6)
                .initialAmount(BigDecimal.valueOf(100))
                .interestRate(BigDecimal.valueOf(6))
                .productId(1).build();
        newCardDto = NewCardRequestDto.builder()
                .accountNumber("accountnumber1")
                .transactionLimit(BigDecimal.valueOf(1000000.00))
                .expirationDate(LocalDate.now())
                .holderName("Ivan Ivanov")
                .digitalWallet(DigitalWallet.APPLEPAY)
                .cardProductId(1)
                .build();
    }

    @Test
    @DisplayName("User sends valid depositAgreementDto to Kafka")
    void sendDepositAgreementDtoToAbs_shouldConsumeNewDepositAgreementDto() throws InterruptedException {
        kafkaProducer.sendDepositAgreementDto(depositAgreementDto);

        boolean messageConsumed = kafkaConsumerStub.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);
    }

    @Test
    @DisplayName("User sends valid NewCardRequestDto to Kafka")
    void sendNewCardRequestDto_shouldConsumeNewCardResponseDto() throws InterruptedException {
        kafkaProducer.sendNewCardRequestDto(newCardDto);

        boolean messageConsumed = kafkaConsumerStub.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);
    }
}

