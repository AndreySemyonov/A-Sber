package ru.astondevs.asber.moneytransferservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity that stores information about transfer details. Binds with {@link TransferType} and
 * {@link TransferSystem}.
 */
@Table(name = TransferDetails.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransferDetails {

    public static final String TABLE_NAME = "transfer_details";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    @NotNull
    @Column(name = "sender_id")
    private UUID senderId;

    @Column(name = "receiver_id")
    private UUID receiverId;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "transfer_type_id",
        referencedColumnName = "transfer_type_id",
        foreignKey = @ForeignKey(name = "fk_transfer_details_on_transfer_type"))
    private TransferType transferTypeId;

    @NotNull
    @Column(name = "transfer_sum")
    private BigDecimal transferSum;

    @NotNull
    @Column(name = "currency_from")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyFrom;

    @NotNull
    @Column(name = "currency_to")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyTo;

    @NotNull
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @NotNull
    @Column(name = "transfer_exch_sum")
    private BigDecimal transferExchSum;

    @NotNull
    @Column(name = "commission")
    private Integer commission;

    @Column(name = "message")
    private String message;

    @NotNull
    @Column(name = "transfer_date")
    private LocalDate transferDate;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "transfer_system_id",
            referencedColumnName = "transfer_system_id",
            foreignKey = @ForeignKey(name = "fk_transfer_details_on_transfer_system"))
    private TransferSystem transferSystemId;

    @NotNull
    @Column(name = "is_favourite")
    private Boolean isFavourite;

    @NotNull
    @Column(name = "is_auto")
    private Boolean isAuto;

    @Column(name = "periodicity")
    private Integer periodicity;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @NotNull
    @Column(name = "purpose_of_transfer")
    private String purposeOfTransfer;

    @Column(name = "authorization_code")
    private Integer authorizationCode;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ToString.Exclude
    @OneToOne(mappedBy = "transferDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Transfer transfer;

    /**
     * Method that synchronizes both ends bidirectional association, binds Transfer and
     * TransferDetails.
     *
     * @param transfer biding entity
     */
    public void setTransfer(Transfer transfer) {
        if (transfer == null) {
            if (this.transfer != null) {
                this.transfer.setTransferDetails(null);
            }
        } else {
            transfer.setTransferDetails(this);
        }
        this.transfer = transfer;
    }
}
