package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Part of data transfer object that is used in {@link ClientCreditResponseDto}
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditsForClientCredit {

    /**
     * Credit id for account.
     */
    @NotNull
    UUID creditId;
    /**
     * Product name of credit order for account.
     */
    @Size(max = 30)
    @NotNull
    String name;
    /**
     * Account principal debt.
     */
    @NotNull
    BigDecimal principalDebt;
    /**
     * Credit limit for account.
     */
    @NotNull
    BigDecimal creditLimit;
    /**
     * Credit currency code for account.
     */
    @NotNull
    CurrencyCode currencyCode;
    /**
     * Agreement termination date of credit for account.
     */
    @NotNull
    LocalDate terminationDate;
}
