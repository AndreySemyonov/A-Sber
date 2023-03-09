package ru.astondevs.asber.infoservice.entity;

import java.sql.Time;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * Description of the bank branch.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = BankBranch.TABLE_NAME)
public class BankBranch {

    public static final String TABLE_NAME = "bank_branch";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "insurance")
    private Boolean insurance;

    @Column(name = "consultation")
    private Boolean consultation;

    @Column(name = "replenish_account")
    private Boolean replenishAccount;

    @Column(name = "replenish_card")
    private Boolean replenishCard;

    @Column(name = "ramp")
    private Boolean ramp;

    @Column(name = "exotic_currency")
    private Boolean exoticCurrency;

    @Column(name = "currency_exchange")
    private Boolean currencyExchange;

    @Column(name = "accept_payment")
    private Boolean acceptPayment;

    @Column(name = "money_transfer")
    private Boolean moneyTransfer;

    @Column(name = "cash_withdraw")
    private Boolean cashWithdraw;

    @Column(name = "work_at_weekends")
    private Boolean workAtWeekends;

    @Column(name = "closing_time")
    private Time closingTime;

    @Column(name = "opening_time")
    private Time openingTime;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "branch_address")
    private String branchAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "branch_coordinates")
    private String branchCoordinates;

    @Column(name = "branch_number")
    private String branchNumber;

    @Column(name = "bank_branch_type")
    private String bankBranchType;

}
