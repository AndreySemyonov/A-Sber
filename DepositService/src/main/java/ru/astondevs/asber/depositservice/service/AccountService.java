package ru.astondevs.asber.depositservice.service;

import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Service for {@link Account}.
 */
public interface AccountService {
    /**
     * Method that gets set of accounts by client id, containing information about client deposits.
     * @param clientId Client id from {@link Account}
     * @return {@link Set} of {@link Account}
     */
    Set<Account> getClientDeposits(UUID clientId);

    /**
     * Method that gets set of accounts by client id, containing information about client cards.
     * @param clientId Client id from {@link Account}
     * @return {@link Set} of {@link Account}
     */
    Set<Account> getClientCards(UUID clientId);

    /**
     *  Method that gets list of accounts by client id.
     * @param clientId Client id from {@link Account}
     * @return {@link List} of {@link Account}
     */
    List<Account> getAccounts(UUID clientId);

    /**
     * Method that gets account by account number.
     * @param accountNumber Account number from {@link Account}
     * @return {@link Account}
     */
    Account getAccountByAccountNumber(String accountNumber);

    /**
     * Method that gets account by agreement id, client id, card id.
     * @param agreementId Agreement id from {@link Agreement}
     * @param clientId Client id from {@link Account}
     * @param cardId Card id from {@link Card}
     * @return {@link Account}
     */
    Account getClientDeposit(UUID agreementId, UUID clientId, UUID cardId);

    /**
     * Method that gets account by card number.
     * @param cardNumber Card number from {@link Card}
     * @return {@link Account}
     */
    Account getAccountByCardNumber(String cardNumber);

    /**
     * Method that gets accounts count by clientId.
     * @param clientId Client id from {@link Account}
     * @return {@link Integer}
     */
    Integer getDepositsCount(UUID clientId);
}
