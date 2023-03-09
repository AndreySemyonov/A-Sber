package ru.astondevs.asber.depositservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.depositservice.entity.enums.SchemaName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity that stores information about products.
 */
@Table(name = Product.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    public static final String TABLE_NAME = "product";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 30)
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "schema_name")
    private SchemaName schemaName;

    @Column(name = "interest_rate_early")
    private BigDecimal interestRateEarly;

    @NotNull
    @Column(name = "is_capitalization")
    private Boolean isCapitalization;

    @Column(name = "amount_min")
    private BigDecimal amountMin;

    @Column(name = "amount_max")
    private BigDecimal amountMax;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code")
    private CurrencyCode currencyCode;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "is_revocable")
    private Boolean isRevocable;

    @NotNull
    @Column(name = "min_interest_rate")
    private BigDecimal minInterestRate;

    @NotNull
    @Column(name = "max_interest_rate")
    private BigDecimal maxInterestRate;

    @NotNull
    @Column(name = "min_duration_months")
    private Integer minDurationMonths;

    @NotNull
    @Column(name = "max_duration_months")
    private Integer maxDurationMonths;

    @Column(name = "active_since")
    private LocalDate activeSince;

    @Column(name = "active_until")
    private LocalDate activeUntil;
}
