package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;

import java.util.List;
import java.util.UUID;

/**
 * Service that works with {@link PaymentSchedule}.
 */
public interface PaymentScheduleService {

    /**
     * Method that gets list of payment schedules by account id.
     *
     * @param accountId Account id from {@link Account}
     * @return {@link List} of {@link PaymentSchedule}
     */
    List<PaymentSchedule> getPaymentSchedulesByAccountId(UUID accountId);

}
