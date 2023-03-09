package ru.astondevs.asber.creditservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.creditservice.ExternalSystemConfig;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class PaymentScheduleRepositoryTest {

    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

    @Test
    @DisplayName("If user wants to get payment schedule by account id then return it")
    void getPaymentSchedulesByAccountId_shouldReturnListOfPaymentSchedules() {
        UUID accountId = UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001");
        List<PaymentSchedule> paymentSchedulesByAccountId = paymentScheduleRepository.getPaymentSchedulesByAccountId(
            accountId);
        assertEquals(1, paymentSchedulesByAccountId.size());
        assertEquals(UUID.fromString("ce149c48-6e80-11ed-a1eb-0242ac120001"),
            paymentSchedulesByAccountId.get(0).getId());
    }

    @Test
    @DisplayName("If user wants to get payment schedule by non existing account id then return empty list")
    void getPaymentSchedulesByAccountId_shouldReturnEmptyList() {
        UUID accountId = UUID.randomUUID();
        List<PaymentSchedule> paymentSchedulesByAccountId = paymentScheduleRepository.getPaymentSchedulesByAccountId(
            accountId);
        assertEquals(0, paymentSchedulesByAccountId.size());
    }
}