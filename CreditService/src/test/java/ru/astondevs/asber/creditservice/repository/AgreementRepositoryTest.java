package ru.astondevs.asber.creditservice.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.creditservice.ExternalSystemConfig;
import ru.astondevs.asber.creditservice.entity.Agreement;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class AgreementRepositoryTest {

    @Autowired
    private AgreementRepository agreementRepository;

    @Test
    @DisplayName("If user wants to get agreement by credit id then return it")
    void findAgreementByCreditId_shouldReturnAgreement() {
        UUID creditId = UUID.fromString("9093905a-6e6b-11ed-a1eb-0242ac120001");
        Optional<Agreement> optionalAgreement = agreementRepository.findAgreementByCreditId(
            creditId);
        assertDoesNotThrow(optionalAgreement::get);
        assertEquals(UUID.fromString("42e0623c-6e6d-11ed-a1eb-0242ac120001"),
            optionalAgreement.get().getId());
    }

    @Test
    @DisplayName("If user wants to get agreement by non existing credit id then throw no such element exception")
    void findAgreementByCreditId_shouldThrowNoSuchElementException() {
        UUID creditId = UUID.randomUUID();
        Optional<Agreement> optionalAgreement = agreementRepository.findAgreementByCreditId(
            creditId);
        assertThrows(NoSuchElementException.class, optionalAgreement::get);
    }

}