package ru.astondevs.asber.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.depositservice.entity.Agreement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository that stores {@link Agreement} entities.
 */
@Repository
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {
    /**
     * Method that finds list of agreements, having value of auto renewal equals true,
     * end date equals between start and end zoned date time.
     * @param start Start zoned date time
     * @param end End zoned date time
     * @return {@link List} of {@link Agreement}
     */
    List<Agreement> findAgreementsByAutoRenewalIsTrueAndEndDateBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Find {@link Agreement} by agreement number
     * @param number - agreement number
     * @return {@link Agreement}
     */
    Optional<Agreement> findByAgreementNumber(String number);
}
