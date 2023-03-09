package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.dto.CreditCardStatusDto;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.CreditOrder;

import java.util.List;
import java.util.UUID;

/**
 * Service that works with {@link Card}.
 */
public interface CardService {

    /**
     * Method that gets list of cards by client id.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link Card}
     */
    List<Card> getCardsByClientId(UUID clientId);

    /**
     * Method that gets card by card number.
     *
     * @param cardNumber Card number from {@link Card}
     * @return {@link Card}
     */
    Card getCardByCardNumber(String cardNumber);

    /**
     * Method that changes card status for finding card by card number.
     *
     * @param creditCardStatusDto {@link CreditCardStatusDto} with card status and card number
     */
    void changeCardStatus(CreditCardStatusDto creditCardStatusDto);

    /**
     * Method that gets credit card by card id.
     *
     * @param cardId Card id from {@link Card}
     * @return {@link Card}
     */
    Card getCreditCardInfoByCardId(UUID cardId);

}

