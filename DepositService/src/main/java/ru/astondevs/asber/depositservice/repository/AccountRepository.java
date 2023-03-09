package ru.astondevs.asber.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.entity.CardProduct;
import ru.astondevs.asber.depositservice.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Repository that stores {@link Account} entities.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    /**
     * Method that finds set of accounts, having client id,active account, active agreement, default card.
     * @param clientId Client id from {@link Account}
     * @return {@link Set} of {@link Account} that associates with {@link Card}, {@link Agreement}, {@link Product}
     */
    @Query(value = """
            SELECT ac FROM Account ac
            JOIN FETCH ac.cards c
            JOIN FETCH ac.agreements ag
            JOIN FETCH ag.productId p
            WHERE ac.isActive=true AND ac.clientId=:clientId AND
            c.isDefault=true AND ag.isActive=true""")
    Set<Account> findActiveAccountsWithAgreementsAndCards(UUID clientId);

    /**
     * Method that finds account, having client id, card with card id,
     * agreement with agreement id, active account, active agreement, default card.
     * @param clientId Client id from {@link Account}
     * @param agreementId Agreement id from {@link Agreement}
     * @param cardId Card id from {@link Card}
     * @return {@link Optional} wrap of {@link Account} that associates with {@link Card}, {@link Agreement}, {@link Product}
     */
    @Query(value = """               
            SELECT ac FROM Account ac
            JOIN FETCH ac.cards c
            JOIN FETCH ac.agreements ag
            JOIN FETCH ag.productId p
            WHERE ac.clientId=:clientId AND ac.isActive=true AND
            c.isDefault=true AND ag.isActive=true AND c.id=:cardId AND ag.id=:agreementId
            """)
    Optional<Account> findActiveAccount(UUID clientId, UUID agreementId, UUID cardId);

    /**
     * Method that finds set of accounts, having client id, active account.
     * @param clientId Client id from {@link Account}
     * @return {@link Set} of {@link Account} that associates with {@link Card}, {@link CardProduct}
     */
    @Query(value = """
            SELECT ac FROM Account ac
            JOIN FETCH ac.cards c
            JOIN FETCH c.cardProductId cp
            WHERE ac.isActive=true AND ac.clientId=:clientId""")
    Set<Account> findActiveAccountsWithCards(UUID clientId);

    /**
     * Method that finds list of account with required client id.
     * @param clientId Client id from {@link Account}
     * @return {@link List} of {@link Account}
     */
    List<Account> findAccountsByClientId(UUID clientId);

    /**
     * Method that finds account with required account number.
     * @param accountNumber Account number from {@link Account}
     * @return {@link Optional} wrap of account {@link Account}
     */
    Optional<Account> findAccountByAccountNumber(String accountNumber);

    /**
     * Method that finds account, having card with card id.
     * @param cardNumber Card number from {@link Card}
     * @return {@link Optional} wrap of account {@link Account}
     */
    @Query(value = """
            SELECT ac FROM Account ac
            WHERE ac.id = (SELECT c.accountId.id FROM Card c WHERE c.cardNumber = :cardNumber)""")
    Optional<Account> findAccountByCardNumber(String cardNumber);

    /**
     * Method that finds active account count by client id.
     * @param clientId Client id from {@link Account}
     * @return {@link Integer} count of client active accounts {@link Account}
     */
    @Query(value = """
            SELECT COUNT(ac) FROM Account ac WHERE ac.clientId = :clientId AND ac.isActive=TRUE
            """)
    Integer findActiveAccountsCountByClientId(UUID clientId);
}
