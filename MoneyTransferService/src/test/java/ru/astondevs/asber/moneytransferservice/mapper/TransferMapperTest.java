package ru.astondevs.asber.moneytransferservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatusDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.entity.TransferSystem;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TransferMapperImpl.class})
class TransferMapperTest {

    @Autowired
    private TransferMapper transferMapper;

    @Test
    @DisplayName("Convert TransferDetails to Transfer status dto")
    void toTransferStatusDto() {
        TransferType transferType = TransferType.builder().transferTypeName("card")
            .transferTypeId(1111111L)
            .maxTransferSum(BigDecimal.valueOf(1000000))
            .minTransferSum(BigDecimal.valueOf(0))
            .commissionPercent(BigDecimal.valueOf(5))
            .maxCommission(1)
            .minCommission(10).build();

        TransferSystem transferSystem = TransferSystem.builder().transferSystemId(1234L).transferSystemName("TRANSFER SYSTEM").build();

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
            .status(TransferStatus.EXECUTED)
            .isDeleted(false)
            .build();
        TransferStatusDto transferStatusDto = transferMapper.toTransferStatusDto(transferDetails);
        assertNotNull(transferStatusDto);
        assertEquals("EXECUTED", transferStatusDto.getStatus().toString());
    }
    @Test
    @DisplayName("Convert TransferDetails to Transfer status dto")
    void nullToTransferStatusDto() {
        TransferStatusDto transferStatusDto = transferMapper.toTransferStatusDto(null);
        assertNull(transferStatusDto);
    }
}