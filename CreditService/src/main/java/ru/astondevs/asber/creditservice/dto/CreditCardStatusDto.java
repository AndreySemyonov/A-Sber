package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.controller.CreditCardsController;
import ru.astondevs.asber.creditservice.entity.enums.CardStatus;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object that is used in request of
 * {@link CreditCardsController#changeCreditCardStatus(CreditCardStatusDto)} method of
 * {@link CreditCardsController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardStatusDto {

    /**
     * Card number.
     */
    @NotNull
    private String cardNumber;
    /**
     * Card status.
     */
    @NotNull
    private CardStatus cardStatus;
}
