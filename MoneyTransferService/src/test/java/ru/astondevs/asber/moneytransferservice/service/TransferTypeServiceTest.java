package ru.astondevs.asber.moneytransferservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.moneytransferservice.repository.TransferTypeRepository;
import ru.astondevs.asber.moneytransferservice.service.impl.TransferTypeServiceImpl;
import ru.astondevs.asber.moneytransferservice.util.exception.CommissionIsNotDefinedInDbException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TransferTypeServiceTest {

    @InjectMocks
    private TransferTypeServiceImpl transferTypeService;
    @Mock
    private TransferTypeRepository transferTypeRepository;

    private TransferType transferType;
    @BeforeEach
    void setUp() {
        transferType = TransferType.builder()
                .transferTypeId(1111111L)
                .transferTypeName("card")
                .maxTransferSum(BigDecimal.valueOf(1000000.0000))
                .minTransferSum(BigDecimal.ZERO)
                .commissionPercent(BigDecimal.ONE)
                .commissionFix(new BigDecimal("100"))
                .maxCommission(10)
                .minCommission(1)
                .currencyFrom(CurrencyCode.RUB).build();
    }

    @Test
    @DisplayName("If transfer type found by id then return TransferType")
    void getTransferById_shouldReturnTransferType() {
        when(transferTypeRepository.findTransferTypeByTransferTypeId(transferType.getTransferTypeId())).thenReturn(Optional.of(transferType));

        TransferType result = transferTypeService.getTransferTypeById(transferType.getTransferTypeId());
        verify(transferTypeRepository, times(1)).findTransferTypeByTransferTypeId(transferType.getTransferTypeId());
        assertEquals(transferType, result);
    }

    @Test
    @DisplayName("If transfer type not found by id then return EntityNotFoundException")
    void getTransferById_shouldReturnEntityNotFountException() {
        Throwable thrown = catchThrowable(() -> {
            transferTypeService.getTransferTypeById(13234L);
        });
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("If both values of commission fix and commission percent are not null then" +
            " return TransferType")
    void getTransferTypeByTransferTypeNameAndCurrencyFrom_shouldReturnTransferType() {
        when(transferTypeRepository.findTransferTypeByTransferTypeNameAndCurrencyFrom("card", CurrencyCode.RUB))
                .thenReturn(Optional.of(transferType));

        TransferType transferTypeTest = transferTypeService.getTransferTypeByTransferTypeNameAndCurrencyFrom("card", CurrencyCode.RUB);
        verify(transferTypeRepository, times(1)).findTransferTypeByTransferTypeNameAndCurrencyFrom(any(),any());
        assertTrue(transferTypeTest.getCommissionFix().equals(new BigDecimal(100)));
        assertTrue(transferTypeTest.getCommissionPercent().equals(new BigDecimal(1)));
    }

    @Test
    @DisplayName("If both values of commission fix and commission percent are null then throw CommissionIsNotDefinedInDbException")
    void getTransferTypeByTransferTypeNameAndCurrencyFrom_shouldThrowCommissionIsNotDefinedInDbException() {
        transferType.setCommissionFix(null);
        transferType.setCommissionPercent(null);
        when(transferTypeRepository.findTransferTypeByTransferTypeNameAndCurrencyFrom("card", CurrencyCode.RUB))
                .thenReturn(Optional.of(transferType));

        Throwable throwable = catchThrowable(() -> {
            transferTypeService.getTransferTypeByTransferTypeNameAndCurrencyFrom("card", CurrencyCode.RUB);
        });
        assertThat(throwable).isInstanceOf(CommissionIsNotDefinedInDbException.class);
    }

}
