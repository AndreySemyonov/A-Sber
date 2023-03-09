package ru.astondevs.asber.absService.service.generators;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Class that generates agreement number from data time of creating agreement.
 */
@Component
public class AgreementNumberGenerator {

    /**
     * Method that generates agreement number from data time of creating agreement.
     * @return {@link String} generated agreement number
     */
    public String getNewAgreementNumber(LocalDateTime time) {
        return time.getYear() + "/" + time.getMonthValue() + time.getDayOfMonth()
                + "0/" + time.getHour() + "/0" + time.getMinute() + time.getSecond();
    }
}
