package ru.astondevs.asber.depositservice.service.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.repository.AgreementRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * Service that Scheduled.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgreementRenewalScheduler {
    private final AgreementRepository agreementRepository;

    /**
     * Method that updates agreement and date for agreement with auto renewal options.
     */
    @Transactional
    @Scheduled(cron = "0 59 23 * * ?")
    public void checkAutoRenewal() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime zonedDateTimeStart = today.with(LocalTime.MIN);
        LocalDateTime zonedDateTimeEnd = today.with(LocalTime.MAX);
        agreementRepository.findAgreementsByAutoRenewalIsTrueAndEndDateBetween(zonedDateTimeStart, zonedDateTimeEnd)
                .forEach(this::updateAgreementEndDate);
    }

    private void updateAgreementEndDate(Agreement agreement) {
        LocalDateTime newEndTime = agreement.getEndDate().plusDays(
                Duration.between(agreement.getStartDate(), agreement.getEndDate()).toDays());
        agreement.setEndDate(newEndTime);
    }
}
