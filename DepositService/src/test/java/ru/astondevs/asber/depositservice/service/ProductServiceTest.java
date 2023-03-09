package ru.astondevs.asber.depositservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.depositservice.entity.Product;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.depositservice.entity.enums.SchemaName;
import ru.astondevs.asber.depositservice.repository.ProductRepository;
import ru.astondevs.asber.depositservice.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    private Product activeProduct1;
    private Product activeProduct2;

    @BeforeEach
    void setup() {
        activeProduct1 = Product.builder()
                .id(1)
                .name("activeProduct1")
                .schemaName(SchemaName.FIXED)
                .interestRateEarly(new BigDecimal(1))
                .isCapitalization(true)
                .amountMin(new BigDecimal(1000))
                .amountMax(new BigDecimal(1000000))
                .currencyCode(CurrencyCode.EUR)
                .isActive(true)
                .isRevocable(true)
                .minInterestRate(new BigDecimal(1000))
                .maxInterestRate(new BigDecimal(11000))
                .minDurationMonths(1)
                .maxDurationMonths(2)
                .build();
        activeProduct2 = Product.builder()
                .id(2)
                .name("activeProduct2")
                .schemaName(SchemaName.FIXED)
                .interestRateEarly(new BigDecimal(1))
                .isCapitalization(true)
                .amountMin(new BigDecimal(1000))
                .amountMax(new BigDecimal(1000000))
                .currencyCode(CurrencyCode.EUR)
                .isActive(true)
                .isRevocable(true)
                .minInterestRate(new BigDecimal(1000))
                .maxInterestRate(new BigDecimal(11000))
                .minDurationMonths(1)
                .maxDurationMonths(2)
                .build();
    }

    @Test
    @DisplayName("If user wants to get deposit products then return all active products")
    void getProducts_shouldReturnListOfActiveProducts() {
        when(productRepository.findProductsByIsActiveIsTrue()).thenReturn(List.of(activeProduct1, activeProduct2));

        List<Product> result = productService.getProducts();

        verify(productRepository, times(1)).findProductsByIsActiveIsTrue();
        assertEquals(2, result.size());
        assertEquals("activeProduct1", result.get(0).getName());
        assertEquals("activeProduct2", result.get(1).getName());
    }

    @Test
    @DisplayName("If user wants to get deposit product by id")
    void getProductById_shouldReturnProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(activeProduct1));

        Product product = productService.getProductById(1);

        verify(productRepository, times(1)).findById(1);
        assertEquals(activeProduct1, product);
    }
}
