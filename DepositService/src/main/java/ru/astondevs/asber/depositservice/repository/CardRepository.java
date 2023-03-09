package ru.astondevs.asber.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.depositservice.entity.Card;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository that stores {@link Card} entities.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    /**
     * Finds card by card number
     * @return {@link Optional} of {@link Card}
     */
    Optional<Card> findCardByCardNumber(String number);
}
