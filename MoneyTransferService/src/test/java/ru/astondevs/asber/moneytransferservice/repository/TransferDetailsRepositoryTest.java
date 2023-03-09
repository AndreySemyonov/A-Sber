package ru.astondevs.asber.moneytransferservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.astondevs.asber.moneytransferservice.ExternalSystemConfig;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.entity.TransferSystem;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class TransferDetailsRepositoryTest {

    @Autowired
    private TransferDetailsRepository transferDetailsRepository;

    @Autowired
    private TransferTypeRepository transferTypeRepository;

    private TransferDetails transferDetails;

    @BeforeAll
    void setUp() {
        final TransferType transferType = TransferType.builder().transferTypeName("card")
                .transferTypeName("test-transfer")
                .maxTransferSum(BigDecimal.valueOf(1000000))
                .minTransferSum(BigDecimal.valueOf(0))
                .commissionPercent(BigDecimal.valueOf(5))
                .maxCommission(1)
                .minCommission(10)
                .currencyFrom(CurrencyCode.RUB)
                .build();

        final TransferSystem transferSystem = TransferSystem.builder()
                .transferSystemName("TRANSFER SYSTEM").build();

        transferDetails = TransferDetails.builder()
                .senderId(UUID.fromString("38dca868-5eaa-11ed-9b6a-0242ac120002"))
                .receiverId(UUID.fromString("7973767c-5eaa-11ed-9b6a-0242ac120002"))
                .transferTypeId(transferType)
                .transferSum(BigDecimal.valueOf(100))
                .currencyFrom(CurrencyCode.RUB)
                .currencyTo(CurrencyCode.RUB)
                .exchangeRate(BigDecimal.valueOf(1))
                .transferExchSum(BigDecimal.valueOf(1))
                .commission(5)
                .message("hi")
                .transferDate(LocalDate.now())
                .transferSystemId(transferSystem)
                .isFavourite(true)
                .isAuto(true)
                .periodicity(12)
                .startDate(LocalDate.now())
                .expirationDate(LocalDate.now())
                .purposeOfTransfer("private")
                .authorizationCode(111)
                .status(TransferStatus.IN_PROGRESS)
                .isDeleted(false)
                .build();

        transferTypeRepository.save(transferType);
    }

    @Test
    @DisplayName("Finding transfer details by id")
    void testFindById() {
        transferDetailsRepository.save(transferDetails);

        Optional<TransferDetails> transferDetailsOptional = transferDetailsRepository.findById(transferDetails.getId());
        assertTrue(transferDetailsOptional.isPresent());

        TransferDetails resultTransferDetails = transferDetailsOptional.get();
        assertEquals(transferDetails.getId(), resultTransferDetails.getId());
    }

    @Test
    @DisplayName("Finding operations history for clientId")
    void testFindAllBySenderIdOrderByStartDateDesc() {
        List<TransferDetails> operations = transferDetailsRepository.findAllBySenderIdOrderByStartDateDesc(transferDetails.getSenderId());
        operations.forEach(x ->
                assertEquals(x.getSenderId(), transferDetails.getSenderId())
        );
    }
}
