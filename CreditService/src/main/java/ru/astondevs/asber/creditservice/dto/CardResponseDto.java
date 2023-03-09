package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.controller.CreditCardsController;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link CreditCardsController#getCardsByClientId(UUID)} method of {@link CreditCardsController}.
 */

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

    /**
     * Card id.
     */
    @NotNull
    private UUID cardId;
    /**
     * Card number.
     */
    @NotNull
    private String cardNumber;
    /**
     * Card balance.
     */
    @NotNull
    private BigDecimal balance;
    /**
     * Account currency code.
     */
    @NotNull
    private CurrencyCode accountCurrencyCode;
}
