package ru.astondevs.asber.creditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.creditservice.entity.enums.RateBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Entity that stores information about credit product.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Product.TABLE_NAME)
@Check(constraints = """
    max_sum >= min_sum AND
    max_interest_rate >= min_interest_rate AND
    max_period_months >= min_period_months""")
public class Product {

    public static final String TABLE_NAME = "product";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "name", length = 30)
    private String name;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "min_sum")
    private BigDecimal minSum;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "max_sum")
    private BigDecimal maxSum;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", length = 3)
    private CurrencyCode currencyCode;

    @NotNull
    @Digits(integer = 6, fraction = 4)
    @Column(name = "min_interest_rate")
    private BigDecimal minInterestRate;

    @NotNull
    @Digits(integer = 6, fraction = 4)
    @Column(name = "max_interest_rate")
    private BigDecimal maxInterestRate;

    @NotNull
    @Column(name = "need_guarantees")
    private Boolean needGuarantees;

    @NotNull
    @Column(name = "delivery_in_cash")
    private Boolean deliveryInCash;

    @NotNull
    @Column(name = "early_repayment")
    private Boolean earlyRepayment;

    @NotNull
    @Column(name = "need_income_details")
    private Boolean needIncomeDetails;

    @NotNull
    @Column(name = "min_period_months")
    private Integer minPeriodMonths;

    @NotNull
    @Column(name = "max_period_months")
    private Integer maxPeriodMonths;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "details")
    private String details;

    @NotNull
    @Column(name = "calculation_mode", length = 30)
    private String calculationMode;

    @NotNull
    @Column(name = "grace_period_months")
    private Integer gracePeriodMonths;

    @NotNull
    @Column(name = "rate_is_adjustable")
    private Boolean rateIsAdjustable;

    @Enumerated(EnumType.STRING)
    @Column(name = "rate_base", length = 30)
    private RateBase rateBase;

    @Digits(integer = 6, fraction = 4)
    @Column(name = "rate_fix_part")
    private BigDecimal rateFixPart;

    @Digits(integer = 6, fraction = 4)
    @Column(name = "increased_rate")
    private BigDecimal increasedRate;
}
