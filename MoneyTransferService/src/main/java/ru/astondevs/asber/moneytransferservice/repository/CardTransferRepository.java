package ru.astondevs.asber.moneytransferservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.moneytransferservice.entity.CardTransfer;

/**
 * Repository that stores {@link CardTransfer} entities.
 */
public interface CardTransferRepository extends JpaRepository<CardTransfer, Long> {
}
