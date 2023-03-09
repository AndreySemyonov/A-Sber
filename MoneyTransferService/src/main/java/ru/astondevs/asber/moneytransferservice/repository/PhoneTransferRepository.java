package ru.astondevs.asber.moneytransferservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.moneytransferservice.entity.PhoneTransfer;

/**
 * Repository that stores {@link PhoneTransfer} entities.
 */
public interface PhoneTransferRepository extends JpaRepository<PhoneTransfer, Long> {
}
