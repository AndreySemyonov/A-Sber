package ru.astondevs.asber.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.Credit;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository that stores {@link Agreement} entities.
 */
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {

    /**
     * Method that finds agreement with required credit id.
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Optional} wrap of {@link Agreement}
     */
    Optional<Agreement> findAgreementByCreditId(UUID creditId);
}
