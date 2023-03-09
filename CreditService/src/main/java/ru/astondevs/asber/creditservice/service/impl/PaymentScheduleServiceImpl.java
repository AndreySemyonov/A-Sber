package ru.astondevs.asber.creditservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;
import ru.astondevs.asber.creditservice.repository.PaymentScheduleRepository;
import ru.astondevs.asber.creditservice.service.PaymentScheduleService;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;

/**
 * Implementation of {@link PaymentScheduleService}. Works with {@link PaymentScheduleRepository}
 * and {@link PaymentSchedule}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentScheduleServiceImpl implements PaymentScheduleService {

    private final PaymentScheduleRepository paymentScheduleRepository;

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link PaymentScheduleRepository#getPaymentSchedulesByAccountId(UUID)}.
     *
     * @param accountId Account id from {@link Account}
     * @return {@link List} of {@link PaymentSchedule}
     * @throws EntityNotFoundException if account not found
     */
    @Override
    public List<PaymentSchedule> getPaymentSchedulesByAccountId(UUID accountId) {
        log.info("Requested payment schedules for accountId: {}", accountId);

        List<PaymentSchedule> paymentSchedules = paymentScheduleRepository.getPaymentSchedulesByAccountId(
            accountId);

        if (!paymentSchedules.isEmpty()) {
            log.info("Got list of payment schedules, size: {}", paymentSchedules.size());
            return paymentSchedules;
        } else {
            log.error("No payment schedules for given accountId: {}", accountId);
            throw new EntityNotFoundException();
        }
    }
}
