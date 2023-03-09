package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;

import java.util.List;
import java.util.UUID;

/**
 * Facade for credit services.
 */
public interface CreditFacadeService {

    /**
     * Method that gets list of payment schedules by credit id.
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link List} of {@link PaymentSchedule}
     */
    List<PaymentSchedule> getCreditPaymentSchedules(UUID creditId);

}
