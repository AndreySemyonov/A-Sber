package ru.astondevs.asber.moneytransferservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.moneytransferservice.dto.TransferRequestDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.entity.TransferSystem;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;
import ru.astondevs.asber.moneytransferservice.mapper.TransferMapper;
import ru.astondevs.asber.moneytransferservice.mapper.TransferMapperImpl;
import ru.astondevs.asber.moneytransferservice.repository.TransferDetailsRepository;
import ru.astondevs.asber.moneytransferservice.service.impl.TransferServiceImpl;
import ru.astondevs.asber.moneytransferservice.util.exception.InvalidUuidException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TransferServiceTest {

    @InjectMocks
    private TransferServiceImpl transferService;
    @Spy
    private TransferMapper transferMapper = new TransferMapperImpl();
    @Mock
    private TransferDetailsRepository transferDetailsRepository;
    @Mock
    private TransferTypeService transferTypeService;

    private TransferRequestDto transferRequestDtoForPhoneNoAuto;
    private TransferRequestDto transferRequestDtoForCardAuto;
    private TransferType phoneTransferType;
    private TransferType cardTransferType;
    private TransferDetails transferDetails;
    private final List<TransferDetails> transferDetailsList = new ArrayList<>();
    private final UUID clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");

    @BeforeEach
    void setUp() {
        transferRequestDtoForPhoneNoAuto = TransferRequestDto.builder()
                .cardNumber("798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
                .transferTypeId(1111111L)
                .sum(BigDecimal.valueOf(1000))
                .remitterCardNumber("408181057000012")
                .name("nothing")
                .payeeAccountNumber("account1")
                .payeeCardNumber("408181057000014")
                .mobilePhone(Long.parseLong("77777777777"))
                .sumCommission(BigDecimal.ONE)
                .isFavourite(true)
                .periodicity(null)
                .startDate(LocalDate.of(2022, 2, 2))
                .purpose("nothing")
                .currencyExchange(BigDecimal.valueOf(60))
                .build();
        transferRequestDtoForCardAuto = TransferRequestDto.builder()
                .cardNumber("798060ff-ed9b-4745-857c-a7a2f4a2e3a2")
                .transferTypeId(2222222L)
                .sum(BigDecimal.valueOf(1000))
                .remitterCardNumber("408181057000012")
                .name("nothing")
                .payeeAccountNumber("account1")
                .payeeCardNumber("408181057000014")
                .mobilePhone(null)
                .sumCommission(BigDecimal.ONE)
                .isFavourite(true)
                .periodicity(12)
                .startDate(LocalDate.of(2022, 2, 2))
                .purpose("nothing")
                .currencyExchange(BigDecimal.valueOf(60))
                .build();
        phoneTransferType = TransferType.builder()
                .transferTypeId(1111111L)
                .transferTypeName("card")
                .maxTransferSum(BigDecimal.valueOf(1000000.0000))
                .minTransferSum(BigDecimal.ZERO)
                .commissionPercent(BigDecimal.ONE)
                .maxCommission(10)
                .minCommission(1).build();
        cardTransferType = TransferType.builder()
                .transferTypeId(2222222L)
                .transferTypeName("phone")
                .maxTransferSum(BigDecimal.valueOf(1000000.0000))
                .minTransferSum(BigDecimal.ZERO)
                .commissionPercent(BigDecimal.ONE)
                .maxCommission(10)
                .minCommission(1).build();
        transferDetails = TransferDetails.builder()
                .id(1234567L)
                .senderId(UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2"))
                .transferSum(new BigDecimal(1000))
                .currencyFrom(CurrencyCode.USD)
                .currencyTo(CurrencyCode.EUR)
                .exchangeRate(new BigDecimal(2000))
                .transferExchSum(new BigDecimal(100000))
                .commission(50)
                .transferDate(LocalDate.now())
                .isFavourite(true)
                .isAuto(true)
                .startDate(LocalDate.now())
                .purposeOfTransfer("")
                .status(TransferStatus.DRAFT)
                .isDeleted(false)
                .build();
        transferDetailsList.add(transferDetails);
    }

    @DisplayName("If client wants to make non-auto payment phone transfer")
    @Test
    void newTransfer_shouldReturnResponseDtoAndSavePhoneTransfer() {
        when(transferTypeService.getTransferTypeById(any())).thenReturn(phoneTransferType);

        transferService.newTransfer(transferRequestDtoForPhoneNoAuto, clientId);

        verify(transferTypeService, times(1)).getTransferTypeById(
                transferRequestDtoForPhoneNoAuto.getTransferTypeId());
        verify(transferMapper, times(1)).phoneTransferFromTransferRequestDto(any());
        verify(transferMapper, times(0)).cardTransferFromTransferRequestDto(any());
        verify(transferDetailsRepository, times(1)).save(any());
    }

    @DisplayName("If client wants to make non-auto payment phone transfer")
    @Test
    void newTransfer_shouldReturnResponseDtoAndSaveCardTransfer() {
        when(transferTypeService.getTransferTypeById(any())).thenReturn(cardTransferType);

        transferService.newTransfer(transferRequestDtoForCardAuto, clientId);

        verify(transferTypeService, times(1)).getTransferTypeById(
                transferRequestDtoForCardAuto.getTransferTypeId());
        verify(transferMapper, times(1)).cardTransferFromTransferRequestDto(any());
        verify(transferMapper, times(0)).phoneTransferFromTransferRequestDto(any());
        verify(transferDetailsRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("If client wants to update non existing transfer status then throw entity not found exception")
    void updateNonExistingTransferStatus_shouldThrowNotFoundException() {
        when(transferDetailsRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> transferService.updateStatus(1234L, TransferStatus.IN_PROGRESS)
        );
    }

    @Test
    @DisplayName("If client wants to update transfer status then update it")
    void updateTransferStatus_shouldReturnTransferStatusDto() {
        TransferType transferType = TransferType.builder().transferTypeName("card")
                .transferTypeId(1111111L)
                .maxTransferSum(BigDecimal.valueOf(1000000))
                .minTransferSum(BigDecimal.valueOf(0))
                .commissionPercent(BigDecimal.valueOf(5))
                .maxCommission(1)
                .minCommission(10).build();

        TransferSystem transferSystem = TransferSystem.builder().transferSystemId(1234L)
                .transferSystemName("TRANSFER SYSTEM").build();

        TransferDetails transferDetails = TransferDetails.builder().id(1111111L)
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

        when(transferDetailsRepository.findById(any())).thenReturn(Optional.of(transferDetails));

        when(transferDetailsRepository.save(any())).thenReturn(transferDetails);

        TransferDetails actualTransferDetails = transferService.updateStatus(
                transferDetails.getId(),
                TransferStatus.IN_PROGRESS);

        assertNotNull(actualTransferDetails);
        assertEquals(TransferStatus.IN_PROGRESS, actualTransferDetails.getStatus());

    }

    @DisplayName("If client wants to delete transfer")
    @Test
    void setIsDeletedTrue_shouldChangeIsDeletedStatusToTrue() {
        when(transferDetailsRepository.findById(1234567L)).thenReturn(Optional.of(transferDetails));
        when(transferDetailsRepository.save(any())).thenReturn(transferDetails);

        transferService.deleteDraftById(1234567L);
        assertTrue(transferDetails.getIsDeleted());
        verify(transferDetailsRepository, times(1)).findById(any());
        verify(transferDetailsRepository, times(1)).save(any());
    }

    @DisplayName("If client wants to see it's operations history")
    @Test
    void getListOfOperations_shouldReturnTransferDetailsDto() {
        when(transferDetailsRepository.findAllBySenderIdOrderByStartDateDesc(clientId)).thenReturn(transferDetailsList);

        transferService.getClientOperationsHistory(String.valueOf(clientId));
        verify(transferDetailsRepository, times(1)).findAllBySenderIdOrderByStartDateDesc(clientId);
        assertEquals(1, transferDetailsList.size());
    }

    @DisplayName("If client wants to see it's operations history")
    @Test
    void getListOfOperationsForInvalidClientId_shouldThrowException() {
        assertThrows(InvalidUuidException.class,
                () -> transferService.getClientOperationsHistory("123")
        );
    }

    @Test
    @DisplayName("If client wants to add transfer to favorites")
    void changeIsFavouriteStatus_shouldChangeIsFavouriteStatusToTrue() {
        when(transferDetailsRepository.findById(1234567L)).thenReturn(Optional.of(transferDetails));
        transferService.changeIsFavouriteStatus(1234567L, true);
        assertTrue(transferDetails.getIsFavourite());
    }

    @Test
    @DisplayName("If client wants to delete transfer from favorites")
    void changeIsFavouriteStatus_shouldChangeIsFavouriteStatusToFalse() {
        when(transferDetailsRepository.findById(1234567L)).thenReturn(Optional.of(transferDetails));
        transferService.changeIsFavouriteStatus(1234567L, false);
        assertFalse(transferDetails.getIsFavourite());
    }
}