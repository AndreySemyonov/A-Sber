package ru.astondevs.asber.creditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;

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
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity that stores information about credit order in db. Binds with {@link Product}.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CreditOrder.TABLE_NAME)
public class CreditOrder {

    public static final String TABLE_NAME = "credit_order";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Size(max = 20)
    @Column(name = "number")
    private String number;

    @NotNull
    @ToString.Exclude
    @Column(name = "client_id")
    private UUID clientId;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "product_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_credit_order_on_product"))
    private Product product;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private CreditStatus status;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "grace_period_months")
    private Integer periodMonths;

    @NotNull
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "monthly_expenditure")
    private BigDecimal monthlyExpenditure;

    @NotNull
    @Column(name = "employer_identification_number", length = 10)
    private String employerIdentificationNumber;
}
