package ru.astondevs.asber.infoservice.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * Currency exchange rate.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ExchangeRate.TABLE_NAME)
public class ExchangeRate {

    public static final String TABLE_NAME = "exchange_rate";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "is_cross")
    private Boolean isCross;

    @Column(name = "selling_rate")
    @Digits(integer = 10, fraction = 4)
    private BigDecimal sellingRate;

    @Column(name = "buying_rate")
    @Digits(integer = 10, fraction = 4)
    private BigDecimal buyingRate;

    @Column(name = "unit")
    private Integer unit;

    @Column(name = "sign")
    private String sign;

    @Column(name = "name")
    private String name;

    @Column(name = "iso_code")
    private String isoCode;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "update_at")
    private Timestamp updateAt;

}
