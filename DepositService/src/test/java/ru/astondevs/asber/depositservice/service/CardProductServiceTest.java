package ru.astondevs.asber.depositservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.depositservice.entity.CardProduct;
import ru.astondevs.asber.depositservice.entity.enums.CoBrand;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.depositservice.entity.enums.PaymentSystem;
import ru.astondevs.asber.depositservice.entity.enums.PremiumStatus;
import ru.astondevs.asber.depositservice.repository.CardProductRepository;
import ru.astondevs.asber.depositservice.service.impl.CardProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CardProductServiceTest {

    @InjectMocks
    private CardProductServiceImpl cardProductService;
    @Mock
    private CardProductRepository cardProductRepository;

    private CardProduct activeCardProduct1;
    private CardProduct activeCardProduct2;

    @BeforeEach
    void setup() {
        activeCardProduct1 = CardProduct.builder()
                .id(1)
                .cardName("CardName1")
                .paymentSystem(PaymentSystem.MASTERCARD)
                .cashback(new BigDecimal(1))
                .coBrand(CoBrand.AEROFLOT)
                .isVirtual(true)
                .premiumStatus(PremiumStatus.INFINITE)
                .servicePrice(new BigDecimal(2))
                .productPrice(new BigDecimal(3))
                .currencyCode(CurrencyCode.USD)
                .isActive(true)
                .cardDuration(36)
                .build();
        activeCardProduct2 = CardProduct.builder()
                .id(2)
                .cardName("CardName2")
                .paymentSystem(PaymentSystem.MASTERCARD)
                .cashback(new BigDecimal(3))
                .coBrand(CoBrand.AEROFLOT)
                .isVirtual(true)
                .premiumStatus(PremiumStatus.INFINITE)
                .servicePrice(new BigDecimal(4))
                .productPrice(new BigDecimal(5))
                .currencyCode(CurrencyCode.EUR)
                .isActive(true)
                .cardDuration(36)
                .build();
    }

    @Test
    @DisplayName("If user wants to get card products then return all active card products")
    void getCardProducts_shouldReturnListOfActiveCardProducts() {
        when(cardProductRepository.findCardProductsByIsActiveIsTrue()).thenReturn(List.of(activeCardProduct1, activeCardProduct2));

        List<CardProduct> result = cardProductService.getCardProducts();

        verify(cardProductRepository, times(1)).findCardProductsByIsActiveIsTrue();
        assertEquals(2, result.size());
        assertEquals("CardName1", result.get(0).getCardName());
        assertEquals("CardName2", result.get(1).getCardName());
    }

    @Test
    @DisplayName("If user wants to get card product by id")
    void getCardProductById_shouldReturnCardProduct() {
        when(cardProductRepository.findById(1)).thenReturn(Optional.ofNullable(activeCardProduct1));

        CardProduct cardProduct = cardProductService.getCardProductById(1);

        verify(cardProductRepository, times(1)).findById(1);
        assertEquals("CardName1", cardProduct.getCardName());
    }
}
