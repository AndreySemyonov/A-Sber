package ru.astondevs.asber.creditservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;
import ru.astondevs.asber.creditservice.entity.enums.CreditType;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity that stores information about сredit. Binds with {@link CreditOrder}, {@link Agreement}.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Credit.TABLE_NAME)
public class Credit {

    public static final String TABLE_NAME = "credit";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "order_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_credit_on_credit_order"))
    private CreditOrder creditOrder;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "credit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Agreement agreement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 30)
    private CreditType type;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", length = 3)
    private CurrencyCode currencyCode;

    @NotNull
    @Digits(integer = 6, fraction = 4)
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @NotNull
    @Column(name = "personal_guarantees")
    private Boolean personalGuarantees;

    @NotNull
    @Column(name = "grace_period_months")
    private Integer gracePeriodMonths;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private CreditStatus status;

    @Digits(integer = 6, fraction = 4)
    @Column(name = "late_payment_rate")
    private BigDecimal latePaymentRate;

    /**
     * Methods that binds account and paymentSchedule
     *
     * @param agreement biding entity
     */
    public void setAgreement(Agreement agreement) {
        if (agreement == null) {
            if (this.agreement != null) {
                this.agreement.setCredit(null);
            }
        } else {
            agreement.setCredit(this);
        }
        this.agreement = agreement;
    }
}
