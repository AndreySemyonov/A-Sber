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
import ru.astondevs.asber.creditservice.entity.CreditOrder;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class CreditOrderRepositoryTest {

    @Autowired
    private CreditOrderRepository creditOrderRepository;

    @Test
    @DisplayName("If user wants to get credit orders by client id then return it")
    void findCreditOrdersByClientId_shouldReturnCreditOrders() {
        UUID clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        List<CreditOrder> creditOrders = creditOrderRepository.findCreditOrdersByClientId(
            clientId);
        assertEquals(1, creditOrders.size());
        assertEquals("credit_num1", creditOrders.get(0).getNumber());
    }

    @Test
    @DisplayName("If user wants to get credit orders by non existing client id then return empty list")
    void findCreditOrdersByClientId_shouldReturnEmptyList() {
        UUID clientId = UUID.randomUUID();
        List<CreditOrder> creditOrders = creditOrderRepository.findCreditOrdersByClientId(
            clientId);
        assertEquals(0, creditOrders.size());
    }
}