package ru.astondevs.asber.absService.service.generators;

import org.springframework.stereotype.Component;

/**
 * Class generates random card number
 */
@Component
public class CardNumberGenerator {
    /**
     * Method that generates random card 16-digit card number.
     * @return  card number string.
     */
    public String getNewCardNumber() {
        return String.valueOf((long)(Math.random() * Math.pow(10.0, 16)));
    }
}
