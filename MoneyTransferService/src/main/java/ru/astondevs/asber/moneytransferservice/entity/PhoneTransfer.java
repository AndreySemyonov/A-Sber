package ru.astondevs.asber.moneytransferservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity that stores information about phone transfer.
 * Binds with {@link TransferDetails}.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = PhoneTransfer.TABLE_NAME)
@AssociationOverride(name = "transferDetails", foreignKey = @ForeignKey(name = "fk_phone_transfer_on_transfer_details"))
public class PhoneTransfer extends Transfer {

    public static final String TABLE_NAME = "phone_transfer";

    @NotNull
    @Column(name = "receiver_phone")
    private Long receiverPhone;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "phone_code",
            referencedColumnName = "phone_code",
            foreignKey = @ForeignKey(name = "fk_phone_transfer_phone_code"))
    private PhoneCode phoneCode;
}
