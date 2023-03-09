package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Credit;

import java.util.UUID;

/**
 * Service that works with {@link Account}.
 */
public interface AccountService {

    /**
     * Method that gets account by credit id.
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Account}
     */
    Account getAccountByCreditId(UUID creditId);

    /**
     * Method that gets credits count by clientId.
     *
     * @param clientId Client id from {@link Account}
     * @return {@link Integer}
     */
    Integer getCreditsCount(UUID clientId);
}
