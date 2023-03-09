package ru.astondevs.asber.infoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.infoservice.controllers.BankBranchesController;

import javax.validation.constraints.NotNull;
import java.sql.Time;

/**
 * Data transfer object that contains information about bank branch.
 * Uses in {@link BankBranchesController#getBankBranches()}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BankBranchDto {

    @NotNull
    private Boolean insurance;

    @NotNull
    private Boolean consultation;

    @NotNull
    private Boolean replenishAccount;

    @NotNull
    private Boolean replenishCard;

    @NotNull
    private Boolean ramp;

    @NotNull
    private Boolean exoticCurrency;

    @NotNull
    private Boolean currencyExchange;

    @NotNull
    private Boolean acceptPayment;

    @NotNull
    private Boolean moneyTransfer;

    @NotNull
    private Boolean cashWithdraw;

    @NotNull
    private Boolean workAtWeekends;

    @NotNull
    private Time closingTime;

    @NotNull
    private Time openingTime;

    @NotNull
    private Boolean isClosed;

    @NotNull
    private String branchAddress;

    @NotNull
    private String city;

    @NotNull
    private String branchCoordinates;

    @NotNull
    private String branchNumber;

    @NotNull
    private String bankBranchType;
}
