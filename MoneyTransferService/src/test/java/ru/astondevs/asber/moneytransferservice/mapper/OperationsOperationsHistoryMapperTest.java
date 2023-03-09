package ru.astondevs.asber.moneytransferservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.moneytransferservice.dto.TransferDetailsDto;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.entity.TransferSystem;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OperationsHistoryMapperImpl.class})
public class OperationsOperationsHistoryMapperTest {

    @Autowired
    OperationsHistoryMapper operationsHistoryMapper;

    @Test
    @DisplayName("Convert TransferDetails list to TransferDetailsDto list")
    void toTransferStatusDto() {

        TransferType transferType = TransferType.builder().transferTypeName("card")
                .transferTypeId(1111111L)
                .maxTransferSum(BigDecimal.valueOf(1000000))
                .minTransferSum(BigDecimal.valueOf(0))
                .commissionPercent(BigDecimal.valueOf(5))
                .maxCommission(1)
                .minCommission(10).build();

        TransferSystem transferSystem = TransferSystem.builder().transferSystemId(1234L).transferSystemName("TRANSFER SYSTEM").build();

        List<TransferDetails> transferDetails = List.of(
                TransferDetails.builder().id(1111111L)
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
                        .status(TransferStatus.EXECUTED)
                        .isDeleted(false)
                        .build(),
                TransferDetails.builder().id(1111112L)
                        .senderId(UUID.fromString("38dca868-5eaa-11ed-9b6a-0242ac120003"))
                        .receiverId(UUID.fromString("7973767c-5eaa-11ed-9b6a-0242ac120003"))
                        .transferTypeId(transferType)
                        .transferSum(BigDecimal.valueOf(300))
                        .currencyFrom(CurrencyCode.RUB)
                        .currencyTo(CurrencyCode.RUB)
                        .exchangeRate(BigDecimal.valueOf(2))
                        .transferExchSum(BigDecimal.valueOf(2))
                        .commission(6)
                        .message("message")
                        .transferDate(LocalDate.now())
                        .transferSystemId(transferSystem)
                        .isFavourite(false)
                        .isAuto(false)
                        .periodicity(10)
                        .startDate(LocalDate.now())
                        .expirationDate(LocalDate.now())
                        .purposeOfTransfer("private")
                        .authorizationCode(112)
                        .status(TransferStatus.EXECUTED)
                        .isDeleted(false)
                        .build()
        );

        List<TransferDetailsDto> operations = operationsHistoryMapper.toTransferDetailsDtoList(transferDetails);
        assertNotNull(operations);
    }
    @Test
    @DisplayName("Convert empty TransferDetails list to TransferDetailsDto list")
    void nullToTransferStatusDto() {
        List<TransferDetailsDto> transferDetailsDto = operationsHistoryMapper.toTransferDetailsDtoList(null);
        assertNull(transferDetailsDto);
    }
}
