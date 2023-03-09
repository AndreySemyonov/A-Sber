package ru.astondevs.asber.infoservice.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;

/**
 * Repository that stores {@link ExchangeRate} entity.
 */
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    Optional<ExchangeRate> findByCurrencyCode(String currencyCode);

}
