package ru.astondevs.asber.depositservice.service.generators;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Component that uses for generate agreement number.
 */
@Component
public class AgreementNumberGenerator {

    /**
     * Method that generates agreement number from data time of creating agreement.
     * @param time data time of creating agreement
     * @return {@link String} generated agreement number
     */
    public String getNewAgreementNumber(ZonedDateTime time) {
        String agreementNumber;
        agreementNumber = time.getYear() + "/" + time.getMonthValue() + time.getDayOfMonth()
                + "0/" + time.getHour() + "/0" + time.getMinute() + time.getSecond();
        return agreementNumber;
    }
}
