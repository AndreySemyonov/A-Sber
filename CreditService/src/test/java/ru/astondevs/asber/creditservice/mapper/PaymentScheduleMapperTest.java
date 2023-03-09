package ru.astondevs.asber.creditservice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.dto.CreditPaymentScheduleDto;
import ru.astondevs.asber.creditservice.dto.PaymentScheduleDto;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;

@ExtendWith(MockitoExtension.class)
public class PaymentScheduleMapperTest {

    private PaymentScheduleMapperImpl paymentScheduleMapper;

    private PaymentSchedule paymentSchedule;

    private Agreement agreement;

    private Account account;

    @BeforeEach
    void setup() {
        paymentScheduleMapper = new PaymentScheduleMapperImpl();
        paymentSchedule = PaymentSchedule.builder()
            .id(UUID.fromString("ce149c48-6e80-11ed-a1eb-0242ac120001"))
            .paymentDate(LocalDate.of(2022, 12, 1))
            .interest(BigDecimal.valueOf(30000 * 0.12))
            .principal(BigDecimal.valueOf(30000))
            .build();

        agreement = Agreement.builder()
            .id(UUID.fromString("42e0623c-6e6d-11ed-a1eb-0242ac120001"))
            .agreementNumber("9093905a-6e6b-11ed-a1eb-0242ac120001")
            .build();

        account = Account.builder()
            .id(UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001"))
            .accountNumber("account_number1")
            .currencyCode(CurrencyCode.USD)
            .build();
    }

    @DisplayName("Convert PaymentSchedule to PaymentScheduleDto")
    @Test
    void paymentScheduleToPaymentScheduleDto() {
        PaymentScheduleDto paymentScheduleDto = paymentScheduleMapper.toPaymentScheduleDto(paymentSchedule);

        assertNotNull(paymentScheduleDto);
        assertEquals(paymentSchedule.getInterest().toString(), paymentScheduleDto.getPaymentInterest());
        assertEquals(paymentSchedule.getPaymentDate().toString(), paymentScheduleDto.getPaymentDate());
        assertEquals(paymentSchedule.getPrincipal().toString(), paymentScheduleDto.getPaymentPrincipal());
    }
    @DisplayName("Convert null to PaymentScheduleDto")
    @Test
    void nullToPaymentScheduleDto() {
        PaymentScheduleDto paymentScheduleDto = paymentScheduleMapper.toPaymentScheduleDto(null);
        assertNull(paymentScheduleDto);
    }

    @DisplayName("Convert Account, Agreement and List<PaymentScheduleDto> to CreditPaymentScheduleDto")
    @Test
    void toCreditPaymentScheduleDto() {
        PaymentScheduleDto paymentScheduleDto = paymentScheduleMapper.toPaymentScheduleDto(
            paymentSchedule);
        List<PaymentScheduleDto> paymentScheduleDtos = new ArrayList<>();
        paymentScheduleDtos.add(paymentScheduleDto);

        CreditPaymentScheduleDto creditPaymentScheduleDto = paymentScheduleMapper.toCreditPaymentScheduleDto(
            paymentScheduleDtos, account, agreement);

        assertNotNull(creditPaymentScheduleDto);
        assertEquals(account.getAccountNumber(), creditPaymentScheduleDto.getAccountNumber());
        assertEquals(agreement.getId().toString(), creditPaymentScheduleDto.getAgreementId());
        assertEquals(paymentSchedule.getInterest().toString(),
            creditPaymentScheduleDto.getPayments().get(0).getPaymentInterest());
        assertEquals(paymentSchedule.getPrincipal().toString(),
            creditPaymentScheduleDto.getPayments().get(0).getPaymentPrincipal());
    }

    @DisplayName("Convert null to creditPaymentScheduleDto")
    @Test
    void nullToCreditPaymentScheduleDto() {
        CreditPaymentScheduleDto creditPaymentScheduleDto = paymentScheduleMapper.toCreditPaymentScheduleDto(
            null, null, null);
        assertNull(creditPaymentScheduleDto);
    }

}
