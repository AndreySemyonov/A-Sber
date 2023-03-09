package ru.astondevs.asber.creditservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.repository.AgreementRepository;
import ru.astondevs.asber.creditservice.service.impl.AgreementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AgreementServiceTest {

    @InjectMocks
    private AgreementServiceImpl agreementService;

    @Mock
    private AgreementRepository agreementRepository;

    private Agreement agreement;

    private final UUID existingCreditUUID = UUID.fromString("9093905a-6e6b-11ed-a1eb-0242ac120001");

    private final UUID nonExistingCreditUUID = UUID.fromString(
        "8093905a-6e6b-11ed-a1eb-0242ac120001");

    @BeforeEach
    void setup() {
        agreement = Agreement.builder()
            .id(UUID.fromString("42e0623c-6e6d-11ed-a1eb-0242ac120001"))
            .agreementNumber("9093905a-6e6b-11ed-a1eb-0242ac120001")
            .build();
    }

    @DisplayName("If client wants to see agreement with specified credit id then return it")
    @Test
    void getAccountByCreditId_shouldReturnAccount() {
        when(agreementRepository.findAgreementByCreditId(any())).thenReturn(Optional.of(agreement));

        Agreement resultAgreement = agreementService.getAgreementByCreditId(existingCreditUUID);

        assertEquals("9093905a-6e6b-11ed-a1eb-0242ac120001", resultAgreement.getAgreementNumber());
        assertEquals(UUID.fromString("42e0623c-6e6d-11ed-a1eb-0242ac120001"), resultAgreement.getId());
    }

    @DisplayName("If client wants to see agreement with non existing credit id then expect EntityNotFoundException")
    @Test
    void getAccountByCreditId_shouldReturnEntityNotFoundError() {
        when(agreementRepository.findAgreementByCreditId(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> agreementService.getAgreementByCreditId(
            nonExistingCreditUUID));
    }



}
