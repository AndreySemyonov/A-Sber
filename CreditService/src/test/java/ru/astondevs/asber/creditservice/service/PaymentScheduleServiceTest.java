package ru.astondevs.asber.creditservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;
import ru.astondevs.asber.creditservice.repository.PaymentScheduleRepository;
import ru.astondevs.asber.creditservice.service.impl.PaymentScheduleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PaymentScheduleServiceTest {

    @InjectMocks
    private PaymentScheduleServiceImpl paymentScheduleService;

    @Mock
    private PaymentScheduleRepository paymentScheduleRepository;

    private List<PaymentSchedule> paymentSchedules;

    private UUID paymentScheduleId = UUID.fromString("ce149c48-6e80-11ed-a1eb-0242ac120001");

    private UUID existingAccountId = UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001");

    @BeforeEach
    void setup() {
        paymentSchedules = new ArrayList<>();
        PaymentSchedule paymentSchedule = PaymentSchedule.builder()
            .id(paymentScheduleId)
            .paymentDate(LocalDate.of(2022, 12, 1))
            .interest(BigDecimal.valueOf(30000 * 0.12))
            .principal(BigDecimal.valueOf(30000))
            .build();
        paymentSchedules.add(paymentSchedule);
    }

    @DisplayName("If client wants to see payment schedules with account id then return it")
    @Test
    void getPaymentSchedulesByAccountId_shouldReturnPaymentSchedule() {
        when(paymentScheduleRepository.getPaymentSchedulesByAccountId(any())).thenReturn(
            paymentSchedules);

        List<PaymentSchedule> paymentSchedulesList = paymentScheduleService.getPaymentSchedulesByAccountId(
            existingAccountId);

        assertEquals(1, paymentSchedulesList.size());
        assertEquals(paymentScheduleId, paymentSchedulesList.get(0).getId());
        assertEquals(BigDecimal.valueOf(30000), paymentSchedulesList.get(0).getPrincipal());
    }

    @DisplayName("If client wants to see payment schedules with non existing account id then expect EntityNotFoundException")
    @Test
    void getPaymentSchedulesByAccountId_shouldReturnEntityNotFoundException() {
        when(paymentScheduleRepository.getPaymentSchedulesByAccountId(any())).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class,
            () -> paymentScheduleService.getPaymentSchedulesByAccountId(existingAccountId));
    }

}
