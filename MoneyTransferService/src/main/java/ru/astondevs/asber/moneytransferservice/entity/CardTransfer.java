package ru.astondevs.asber.moneytransferservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;


/**
 * Entity that stores information about card transfer.
 * Binds with {@link TransferDetails}.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = CardTransfer.TABLE_NAME)
@AssociationOverride(name = "transferDetails", foreignKey = @ForeignKey(name = "fk_card_transfer_on_transfer_details"))
public class CardTransfer extends Transfer {

    public static final String TABLE_NAME = "card_transfer";



}
