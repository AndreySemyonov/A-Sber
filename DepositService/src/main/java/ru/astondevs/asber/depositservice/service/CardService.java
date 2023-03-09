package ru.astondevs.asber.depositservice.service;

import ru.astondevs.asber.depositservice.dto.CardStatusDto;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;
import ru.astondevs.asber.depositservice.entity.Card;

import java.util.UUID;

/**
 * Service for {@link Card}.
 */
public interface CardService {
    /**
     * Method that updates card status by card id.
     * @param cardId Card id from {@link Card}
     * @param cardStatusDto {@link CardStatusDto} with new value of card status from
     */
    void changeCardStatus(UUID cardId, CardStatusDto cardStatusDto);

    /**
     * Method that maps {@link NewCardResponseDto} to {@link Card}
     *
     * @param message - {@link NewCardResponseDto}
     * @return {@link Card}
     */
    Card createCardFromAbs(NewCardResponseDto message);
}
