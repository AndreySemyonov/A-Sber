package ru.astondevs.asber.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;
import ru.astondevs.asber.creditservice.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository that stores {@link Card} entities.
 */
public interface CardRepository extends JpaRepository<Card, UUID> {

    /**
     * Method that finds list of cards with required client id.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link Card} that associates with {@link Account}, {@link Credit},
     * {@link CreditOrder}
     */
    @Query(value = """
        select c from Card c
        left join fetch c.account ac
        left join Credit credit on ac.credit = credit
        left join CreditOrder co on credit.creditOrder = co
        where co.clientId=:clientId
        """)
    List<Card> findCardsByClientId(UUID clientId);

    /**
     * Method that finds card with required card number.
     *
     * @param cardNumber Card number from {@link Card}
     * @return {@link Optional} wrap of {@link Card}
     */
    Optional<Card> findCardByCardNumber(String cardNumber);

    /**
     * Method that finds card with required card id.
     *
     * @param cardId Card id from {@link Card}
     * @return {@link Optional} wrap of {@link Card} that associates with {@link Account},
     * {@link Credit},{@link Agreement}, {@link CreditOrder}, {@link Product}
     */
    @Query(value = """
        select c from Card c
        left join fetch c.account ac
        left join fetch ac.credit cr
        left join fetch cr.agreement ag
        left join fetch cr.creditOrder co
        left join fetch co.product
        where c.id=:cardId
        """)
    Optional<Card> findCardByCardId(UUID cardId);

    /**
     * Method that finds card with required card id
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Optional} wrap of {@link Card} that associates with {@link Account},
     * {@link Credit}, {@link Agreement}, {@link PaymentSchedule}, {@link CreditOrder},
     * {@link Product}
     */
    @Query(value = """
        select c from Card c
        left join fetch c.account ac
        left join fetch ac.paymentSchedule ps
        left join fetch ac.credit cr
        left join fetch cr.agreement ag
        left join fetch cr.creditOrder co
        left join fetch co.product
        where cr.id=:creditId
        """)
    Optional<Card> getFullCardInfoByCreditId(UUID creditId);
}
