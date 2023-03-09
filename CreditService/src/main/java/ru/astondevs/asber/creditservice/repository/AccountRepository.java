package ru.astondevs.asber.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.entity.Product;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

/**
 * Repository that stores {@link Account} entities.
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {

    /**
     * Method that finds list of accounts, having credit order with client id, status account equals
     * like active.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link Account} that associates with {@link Credit},
     * {@link CreditOrder}, {@link Agreement}, {@link Product}
     */
    @Query(value = """
        select ac from Account ac
        join fetch ac.credit c
        join fetch c.creditOrder co
        join fetch c.agreement a
        join fetch co.product p
        where co.clientId=:clientId and c.status ='ACTIVE'
        """)
    List<Account> findAccountAndActiveCreditAndAgreementAndProductByClientId(UUID clientId);

    /**
     * Method that finds account, having credit id.
     *
     * @param creditId Client id from {@link CreditOrder}
     * @return {@link Optional} wrap of {@link Account}
     */
    Optional<Account> findAccountByCreditId(UUID creditId);

    /**
     * Method that finds active account count by client id.
     *
     * @param clientId Client id from {@link Account}
     * @return {@link Integer} count of client active accounts {@link Account}
     */
    @Query(value = """
        SELECT COUNT(ac) FROM Account ac
         WHERE ac.credit.creditOrder.clientId=:clientId AND ac.isActive=TRUE""")
    Integer findActiveAccountsCountByClientId(UUID clientId);
}
