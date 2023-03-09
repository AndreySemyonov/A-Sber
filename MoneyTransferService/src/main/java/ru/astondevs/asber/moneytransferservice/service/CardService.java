package ru.astondevs.asber.moneytransferservice.service;

import ru.astondevs.asber.moneytransferservice.controller.InfoController;
import ru.astondevs.asber.moneytransferservice.dto.AccountNumberDto;

/**
 * Service for {@link InfoController}
 */
public interface CardService {
    /**
     * Method that gets account number by card number
     * @param cardNumber card number param from request
     * @return {@link AccountNumberDto} that contains account number.
     */
    public AccountNumberDto getAccountNumberIfCardNumberIsPresent(String cardNumber);
}
