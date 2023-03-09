package ru.astondevs.asber.creditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity that stores information about operation. Binds with {@link Account},
 * {@link OperationType}.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Operation.TABLE_NAME)
public class Operation {

    public static final String TABLE_NAME = "operation";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "account_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_operation_on_account"))
    private Account account;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "operation_type_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_operation_on_operation_type"))
    private OperationType operationType;

    @NotNull
    @Column(name = "sum")
    private BigDecimal sum;

    @NotNull
    @Column(name = "completed_at")
    private LocalDate completedAt;

    @NotNull
    @Column(name = "details")
    private String details;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", length = 3)
    private CurrencyCode currencyCode;
}
