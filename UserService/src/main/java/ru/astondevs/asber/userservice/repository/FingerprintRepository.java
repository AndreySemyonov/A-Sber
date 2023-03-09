package ru.astondevs.asber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.userservice.entity.Fingerprint;

/**
 * Repository that stores {@link Fingerprint} entities.
 */
@Repository
public interface FingerprintRepository extends JpaRepository<Fingerprint, Integer> {
}
