package ru.astondevs.asber.creditservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
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
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity that stores information about client account. Binds with {@link Credit},
 * {@link PaymentSchedule}.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Account.TABLE_NAME)
public class Account {

    public static final String TABLE_NAME = "account";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "credit_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_account_on_credit"))
    private Credit credit;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PaymentSchedule paymentSchedule;

    @NotNull
    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Digits(integer = 19, fraction = 4)
    @Column(name = "principal_debt")
    private BigDecimal principalDebt;

    @Digits(integer = 19, fraction = 4)
    @Column(name = "interest_debt")
    private BigDecimal interestDebt;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", length = 3)
    private CurrencyCode currencyCode;

    @Digits(integer = 19, fraction = 4)
    @Column(name = "outstanding_principal_debt")
    private BigDecimal outstandingPrincipalDebt;

    @Digits(integer = 19, fraction = 4)
    @Column(name = "outstanding_interest_debt")
    private BigDecimal outstandingInterestDebt;

    /**
     * Methods that synchronizes both ends bidirectional association, binds account and
     * paymentSchedule.
     *
     * @param paymentSchedule biding entity
     */
    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        if (paymentSchedule == null) {
            if (this.paymentSchedule != null) {
                this.paymentSchedule.setAccount(null);
            }
        } else {
            paymentSchedule.setAccount(this);
        }
        this.paymentSchedule = paymentSchedule;
    }
}
