package ru.astondevs.asber.creditservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.entity.Product;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.creditservice.entity.enums.RateBase;
import ru.astondevs.asber.creditservice.repository.ProductRepository;
import ru.astondevs.asber.creditservice.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productService;

    private Product activeProduct1;

    private Product activeProduct2;

    @BeforeEach
    void setUp(){
        activeProduct1 = Product.builder()
                .id(1)
                .name("activeProduct")
                .minSum(new BigDecimal(10))
                .maxSum(new BigDecimal(1000))
                .currencyCode(CurrencyCode.EUR)
                .minInterestRate(new BigDecimal(1000))
                .maxInterestRate(new BigDecimal(11000))
                .needGuarantees(true)
                .deliveryInCash(false)
                .earlyRepayment(true)
                .needIncomeDetails(true)
                .minPeriodMonths(4)
                .maxPeriodMonths(5)
                .isActive(true)
                .details("details")
                .calculationMode("calculationMode")
                .gracePeriodMonths(3)
                .rateIsAdjustable(true)
                .rateBase(RateBase.RUONIA)
                .rateFixPart(new BigDecimal(112))
                .increasedRate(new BigDecimal(323))
                .build();

        activeProduct2 = Product.builder()
                .id(2)
                .name("notActiveProduct")
                .minSum(new BigDecimal(10))
                .maxSum(new BigDecimal(1000))
                .currencyCode(CurrencyCode.EUR)
                .minInterestRate(new BigDecimal(1000))
                .maxInterestRate(new BigDecimal(11000))
                .needGuarantees(true)
                .deliveryInCash(false)
                .earlyRepayment(true)
                .needIncomeDetails(true)
                .minPeriodMonths(4)
                .maxPeriodMonths(5)
                .isActive(true)
                .details("details")
                .calculationMode("calculationMode")
                .gracePeriodMonths(3)
                .rateIsAdjustable(true)
                .rateBase(RateBase.RUONIA)
                .rateFixPart(new BigDecimal(112))
                .increasedRate(new BigDecimal(323))
                .build();
    }

    @Test
    void getActiveProductsShouldReturnListOfTwo(){

        List<Product> productListFromDb = List.of(activeProduct1, activeProduct2);

        when(productRepository.findProductsByIsActive(true)).thenReturn(productListFromDb);

        List<Product> activeProductList = productService.getActiveProducts();

        assertThat(activeProductList.size()).isEqualTo(productListFromDb.size());
        assertThat(activeProductList.get(1).getId()).isEqualTo(productListFromDb.get(1).getId());
    }




}
