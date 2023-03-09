package ru.astondevs.asber.depositservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.depositservice.entity.enums.CoBrand;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.depositservice.entity.enums.PaymentSystem;
import ru.astondevs.asber.depositservice.entity.enums.PremiumStatus;

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

/**
 *  Entity that stores information about card products.
 */
@Table(name = CardProduct.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardProduct {

    public static final String TABLE_NAME = "card_product";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 30)
    @Column(name = "card_name")
    private String cardName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_system")
    private PaymentSystem paymentSystem;

    @Column(name = "cashback")
    private BigDecimal cashback;

    @Enumerated(EnumType.STRING)
    @Column(name = "co_brand")
    private CoBrand coBrand;

    @NotNull
    @Column(name = "is_virtual")
    private Boolean isVirtual;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "premium_status")
    private PremiumStatus premiumStatus;

    @NotNull
    @Column(name = "service_price")
    private BigDecimal servicePrice;

    @NotNull
    @Column(name = "product_price")
    private BigDecimal productPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code")
    private CurrencyCode currencyCode;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "card_duration")
    private Integer cardDuration;
}
