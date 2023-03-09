package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.entity.CreditOrder;

import java.util.List;
import java.util.UUID;

/**
 * Service that works with {@link Account}, {@link Card} for getting client credit.
 */
public interface CreditService {

    /**
     * Method that gets list of client credits by client id.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link Account}
     */
    List<Account> getClientCredits(UUID clientId);

    /**
     * Method that gets client credit by credit id.
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Card}
     */
    Card getCredit(UUID creditId);
}
