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
import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.DigitalWallet;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.entity.Product;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.depositservice.entity.enums.SchemaName;
import ru.astondevs.asber.depositservice.repository.AgreementRepository;
import ru.astondevs.asber.depositservice.repository.CardRepository;
import ru.astondevs.asber.depositservice.util.KafkaProducerStub;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {ExternalSystemConfig.PostgresInitializer.class,
        ExternalSystemConfig.KafkaInitializer.class})
public class KafkaConsumerTest {
    @Autowired
    private AgreementRepository agreementRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private KafkaProducerStub kafkaProducerStub;
    private Agreement agreement;
    private Product product;
    private AbsDepositAgreementMessageDto absDepositAgreementMessageDto;
    private NewCardResponseDto newCardResponseDto;

    @BeforeEach
    void setup() {
        product = Product.builder()
                .id(1)
                .name("activeProduct1")
                .schemaName(SchemaName.FIXED)
                .interestRateEarly(new BigDecimal(1))
                .isCapitalization(true)
                .amountMin(new BigDecimal(1000))
                .amountMax(new BigDecimal(1000000))
                .currencyCode(CurrencyCode.EUR)
                .isActive(true)
                .isRevocable(true)
                .minInterestRate(new BigDecimal(1000))
                .maxInterestRate(new BigDecimal(11000))
                .minDurationMonths(1)
                .maxDurationMonths(2)
                .activeUntil(LocalDate.now().plusMonths(2))
                .build();
        agreement = Agreement.builder()
                .id(UUID.fromString("b289e118-5eae-11ed-9b6a-0242ac120002"))
                .agreementNumber("agreementNumber")
                .interestRate(new BigDecimal(2))
                .startDate(LocalDateTime.from(ZonedDateTime.of(2022, 11, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .endDate(LocalDateTime.from(ZonedDateTime.of(2022, 12, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .initialAmount(new BigDecimal(2))
                .currentBalance(new BigDecimal(2))
                .isActive(true)
                .autoRenewal(true)
                .productId(product)
                .build();
        absDepositAgreementMessageDto = AbsDepositAgreementMessageDto.builder()
                .startDate(LocalDateTime.from(ZonedDateTime.of(2022, 11, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .endDate(LocalDateTime.from(ZonedDateTime.of(2022, 12, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .agreementNumber("agreementNumber")
                .productId(1)
                .isActive(true)
                .initialAmount(new BigDecimal("2"))
                .cardNumber("408181057000012")
                .autoRenewal(true)
                .interestRate(new BigDecimal("2"))
                .build();
        newCardResponseDto = NewCardResponseDto.builder()
                .cardNumber("1234567812345678")
                .isDefault(true)
                .accountNumber("accountnumber1")
                .transactionLimit(BigDecimal.valueOf(1000000.00))
                .expirationDate(LocalDate.now())
                .holderName("Ivan Ivanov")
                .digitalWallet(DigitalWallet.APPLEPAY)
                .cardProductId(1)
                .build();
    }

    @Test
    @DisplayName("Consumer class receives absDepositAgreementMessageDto from Kafka, saves it to DB")
    void consumeAbsDepositAgreementMessageDto_shouldSaveNewAgreement() throws InterruptedException {
        kafkaProducerStub.sendDepositAgreementDto(absDepositAgreementMessageDto);
        Thread.sleep(5000);
        Agreement testAgreement = agreementRepository.findByAgreementNumber(absDepositAgreementMessageDto.getAgreementNumber()).orElseThrow();
        assertNotNull(testAgreement);
    }

    @Test
    @DisplayName("Consumer class receives NewCardRequestDto from Kafka, saves it to DB")
    void consumeNewCardResponseDto_shouldSaveNewCard() throws InterruptedException {
        kafkaProducerStub.sendNewCardResponseDto(newCardResponseDto);
        Thread.sleep(5000);
        Optional<Card> card = cardRepository.findCardByCardNumber(newCardResponseDto.getCardNumber());
        assertNotNull(card.get());
    }

}
