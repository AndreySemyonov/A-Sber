package ru.astondevs.asber.moneytransferservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Entity that stores information about transfer type.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TransferType.TABLE_NAME)
public class TransferType {

    public static final String TABLE_NAME = "transfer_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_type_id")
    private Long transferTypeId;

    @NotNull
    @Column(name = "transfer_type_name")
    private String transferTypeName;

    @NotNull
    @Column(name = "min_transfer_sum")
    private BigDecimal minTransferSum;

    @NotNull
    @Column(name = "max_transfer_sum")
    private BigDecimal maxTransferSum;

    @NotNull
    @Column(name = "min_commission")
    private Integer minCommission;

    @NotNull
    @Column(name = "max_commission")
    private Integer maxCommission;

    @Column(name = "commission_percent")
    private BigDecimal commissionPercent;

    @Column(name = "commission_fix")
    private BigDecimal commissionFix;

    @NotNull
    @Column(name = "currency_from")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyFrom;
}
