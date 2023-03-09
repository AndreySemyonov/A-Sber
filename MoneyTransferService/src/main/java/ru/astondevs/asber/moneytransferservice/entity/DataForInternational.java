package ru.astondevs.asber.moneytransferservice.entity;

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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entity that stores information about data for transfer.
 * Binds with {@link CardTransfer}.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DataForInternational.TABLE_NAME)
public class DataForInternational {

    public static final String TABLE_NAME = "data_for_international";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_int_id")
    private Long dataIntId;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "card_transfer_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_data_for_international_on_card_transfer"))
    private CardTransfer cardTransfer;

    @NotNull
    @Column(name = "CVV")
    private Integer cvv;

    @NotNull
    @Column(name = "receiver_name")
    private String receiverName;

    @NotNull
    @Column(name = "receiver_surname")
    private String receiverSurname;

    @NotNull
    @Column(name = "expire_date")
    private LocalDate expireDate;
}
