package ru.astondevs.asber.creditservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.entity.Product;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;
import ru.astondevs.asber.creditservice.repository.CreditOrderRepository;
import ru.astondevs.asber.creditservice.service.impl.CreditOrderServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CreditOrderServiceTest {

    @InjectMocks
    private CreditOrderServiceImpl creditOrderService;
    @Mock
    private CreditOrderRepository creditOrderRepository;

    private UUID clientId;
    private CreditOrder creditOrders;

    @BeforeEach
    void setup() {
        clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        creditOrders = CreditOrder.builder()
                .id(UUID.fromString("8a5cbaa4-6e6b-11ed-a1eb-0242ac120001"))
                .product(Product.builder().id(1).build())
                .status(CreditStatus.ACTIVE)
                .amount(BigDecimal.valueOf(2000000L))
                .periodMonths(36)
                .creationDate(LocalDate.of(2022, 12, 1))
                .build();
    }

    @Test
    @DisplayName("If user wants to get his credit orders and clientId is valid then return CreditOrderResponseDto")
    void getCreditOrders_shouldReturnCreditOrderResponseDto() {
        when(creditOrderRepository.findCreditOrdersByClientId(clientId)).thenReturn(List.of(creditOrders));

        List<CreditOrder> creditOrdersFromDb = creditOrderService.getCreditOrdersByClientId(clientId);

        verify(creditOrderRepository, times(1)).findCreditOrdersByClientId(clientId);
        assertThat(creditOrdersFromDb).isEqualTo(List.of(creditOrders));
    }

    @Test
    @DisplayName("If user wants to get a new credit order then create one, save it and return it")
    void saveNewCreditOrder_shouldReturnNewCreditOrderResponseDto() {
        when(creditOrderRepository.save(creditOrders)).thenReturn(creditOrders);
        CreditOrder savedCreditOrder = creditOrderService.getNewCreditOrder(creditOrders);
        assertThat(savedCreditOrder).isEqualTo(creditOrders);
    }
}
