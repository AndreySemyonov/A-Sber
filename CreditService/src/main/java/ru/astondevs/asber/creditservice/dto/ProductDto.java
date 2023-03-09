package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.controller.CreditProductsController;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.creditservice.entity.enums.RateBase;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Data transfer object that is used in response of
 * {@link CreditProductsController#getActiveCreditProducts()} method of
 * {@link CreditProductsController}.
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    /**
     * Product id.
     */
    @NotNull
    private Integer id;
    /**
     * Product name.
     */
    @NotNull
    private String name;
    /**
     * Product min sum.
     */
    @NotNull
    private BigDecimal minSum;
    /**
     * Product max sum.
     */
    @NotNull
    private BigDecimal maxSum;
    /**
     * Product currency code.
     */
    @NotNull
    private CurrencyCode currencyCode;
    /**
     * Product min interest rate.
     */
    @NotNull
    private BigDecimal minInterestRate;
    /**
     * Product max interest rate.
     */
    @NotNull
    private BigDecimal maxInterestRate;
    /**
     * Boolean value of product needGuarantees.
     */
    @NotNull
    private Boolean needGuarantees;
    /**
     * Boolean value of product deliveryInCash.
     */
    @NotNull
    private Boolean deliveryInCash;
    /**
     * Boolean value of product earlyRepayment.
     */
    @NotNull
    private Boolean earlyRepayment;
    /**
     * Boolean value of product needIncomeDetails.
     */
    @NotNull
    private Boolean needIncomeDetails;
    /**
     * Product min period months.
     */
    @NotNull
    private Integer minPeriodMonths;
    /**
     * Product max period months.
     */
    @NotNull
    private Integer maxPeriodMonths;
    /**
     * Boolean value of product isActive.
     */
    @NotNull
    private Boolean isActive;
    /**
     * Product details.
     */
    @NotNull
    private String details;
    /**
     * Product calculation mode.
     */
    @NotNull
    private String calculationMode;
    /**
     * Product grace period months.
     */
    @NotNull
    private Integer gracePeriodMonths;
    /**
     * Boolean value of product rateIsAdjustable.
     */
    @NotNull
    private Boolean rateIsAdjustable;
    /**
     * Product base rate.
     */
    @NotNull
    private RateBase rateBase;
    /**
     * Product fix part rate.
     */
    @NotNull
    private BigDecimal rateFixPart;
    /**
     * Product increased rate.
     */
    @NotNull
    private BigDecimal increasedRate;

}
