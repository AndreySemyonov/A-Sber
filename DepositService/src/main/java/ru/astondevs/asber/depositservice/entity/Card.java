package ru.astondevs.asber.depositservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.depositservice.entity.enums.CardStatus;
import ru.astondevs.asber.depositservice.entity.enums.DigitalWallet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 *  Entity that stores information about cards.
 *  Binds with {@link Account}, {@link CardProduct}.
 */
@Table(name = Card.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    public static final String TABLE_NAME = "card";

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @Size(max = 16)
    @NotNull
    @Column(name = "card_number")
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ToString.Exclude
    private Account accountId;

    @Column(name = "transaction_limit")
    private BigDecimal transactionLimit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CardStatus status;

    @NotNull
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Size(max = 50)
    @NotNull
    @Column(name = "holder_name")
    private String holderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "digital_wallet")
    private DigitalWallet digitalWallet;

    @NotNull
    @Column(name = "is_default")
    private Boolean isDefault;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_product_id")
    @ToString.Exclude
    private CardProduct cardProductId;
}
