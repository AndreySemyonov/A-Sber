package ru.astondevs.asber.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;

import java.util.List;
import java.util.UUID;

/**
 * Repository that stores {@link PaymentSchedule} entities.
 */
public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, UUID> {

    /**
     * Method that finds list of payment schedules with required account id.
     *
     * @param accountId Account id from {@link PaymentSchedule}
     * @return {@link List} of {@link PaymentSchedule}
     */
    List<PaymentSchedule> getPaymentSchedulesByAccountId(UUID accountId);
}
