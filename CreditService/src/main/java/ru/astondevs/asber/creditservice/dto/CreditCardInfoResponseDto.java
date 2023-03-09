package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.controller.CreditCardsController;
import ru.astondevs.asber.creditservice.entity.enums.CardStatus;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.creditservice.entity.enums.PaymentSystem;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link CreditCardsController#getCreditCardInfoByCardId(UUID)} method of
 * {@link CreditCardsController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardInfoResponseDto {

    /**
     * Account number.
     */
    @NotNull
    private String accountNumber;
    /**
     * Card balance.
     */
    @NotNull
    private BigDecimal balance;
    /**
     * Card name of holder.
     */
    @NotNull
    private String holderName;
    /**
     * Card expiration date.
     */
    @NotNull
    private LocalDate expirationDate;
    /**
     * Card payment system.
     */
    @NotNull
    private PaymentSystem paymentSystem;
    /**
     * Card status.
     */
    @NotNull
    private CardStatus status;
    /**
     * Card transaction limit.
     */
    @NotNull
    private BigDecimal transactionLimit;
    /**
     * Product name of current credit order.
     */
    @NotNull
    private String name;
    /**
     * Account principal debt of card.
     */
    private BigDecimal principalDebt;
    /**
     * Credit limit of account.
     */
    @NotNull
    private BigDecimal creditLimit;
    /**
     * Credit currency code of account.
     */
    @NotNull
    private CurrencyCode currencyCode;
    /**
     * Agreement termination date of credit for account.
     */
    @NotNull
    private LocalDate terminationDate;
}
