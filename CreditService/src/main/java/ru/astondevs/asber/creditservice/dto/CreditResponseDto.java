package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.controller.CreditController;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data transfer object that is used in response of {@link CreditController#getCredit(UUID)} method
 * of {@link CreditController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditResponseDto {

    /**
     * Product name of credit order for card account.
     */
    @NotNull
    String name;
    /**
     * Credit currency code for card account.
     */
    @NotNull
    CurrencyCode creditCurrencyCode;
    /**
     * Agreement date of credit for card account.
     */
    @NotNull
    LocalDate agreementDate;
    /**
     * Agreement id of credit for card account.
     */
    @NotNull
    UUID agreementId;
    /**
     * Agreement number of credit for card account.
     */
    @NotNull
    String agreementNumber;
    /**
     * Account number for card.
     */
    @NotNull
    String accountNumber;
    /**
     * Card id.
     */
    UUID cardId;
    /**
     * Card number.
     */
    String cardNumber;
    /**
     * Card balance.
     */
    BigDecimal balance;
    /**
     * Account currency code for card.
     */
    @NotNull
    CurrencyCode accountCurrencyCode;
    /**
     * Credit limit for card account.
     */
    @NotNull
    BigDecimal creditLimit;
    /**
     * Interest rate for card account.
     */
    @NotNull
    BigDecimal interestRate;
    /**
     * Principal debt for card account.
     */
    @NotNull
    BigDecimal principalDebt;
    /**
     * Interest debt for card account.
     */
    @NotNull
    BigDecimal interestDebt;
    /**
     * Payment date of payment schedule debt for card account.
     */
    @NotNull
    LocalDate paymentDate;
    /**
     * Payment principal of payment schedule debt for card account.
     */
    @NotNull
    BigDecimal paymentPrincipal;
    /**
     * Payment interest of payment schedule debt for card account.
     */
    @NotNull
    BigDecimal paymentInterest;
}
