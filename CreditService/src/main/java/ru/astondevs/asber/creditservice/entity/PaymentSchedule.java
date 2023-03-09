package ru.astondevs.asber.creditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity that stores information about payment schedule. Binds with {@link Account}.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = PaymentSchedule.TABLE_NAME)
public class PaymentSchedule {

    public static final String TABLE_NAME = "payment_schedule";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "account_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_payment_schedule_on_account"))
    private Account account;

    @NotNull
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "principal")
    private BigDecimal principal;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "interest")
    private BigDecimal interest;
}
