package ru.astondevs.asber.creditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ru.astondevs.asber.creditservice.entity.enums.CardStatus;
import ru.astondevs.asber.creditservice.entity.enums.CoBrand;
import ru.astondevs.asber.creditservice.entity.enums.PaymentSystem;

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
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity that stores information about сard. Binds with {@link Account}.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Card.TABLE_NAME)
public class Card {

    public static final String TABLE_NAME = "card";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Column(name = "card_number", length = 16)
    private String cardNumber;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "account_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_card_on_account"))
    private Account account;

    @NotNull
    @Column(name = "holder_name", length = 50)
    private String holderName;

    @NotNull
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_system", length = 30)
    private PaymentSystem paymentSystem;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private CardStatus status;

    @NotNull
    @Digits(integer = 19, fraction = 4)
    @Column(name = "transaction_limit")
    private BigDecimal transactionLimit;

    @NotNull
    @Size(max = 30)
    @Column(name = "delivery_point")
    private String deliveryPoint;

    @Column(name = "is_digital_wallet")
    private Boolean isDigitalWallet;

    @NotNull
    @Column(name = "is_virtual")
    private Boolean isVirtual;

    @Enumerated(EnumType.STRING)
    @Column(name = "co_brand", length = 30)
    private CoBrand coBrand;
}
