package ru.astondevs.asber.creditservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.creditservice.dto.CreditCardStatusDto;
import ru.astondevs.asber.creditservice.service.CardService;
import ru.astondevs.asber.creditservice.service.impl.CardServiceImpl;

/**
 * Class that throws exception if method
 * {@link CardServiceImpl#changeCardStatus(CreditCardStatusDto)} of service {@link CardService} can
 * not change card status.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotChangeCardStatusException extends RuntimeException {

    /**
     * Class constructor specifying messages for exception
     */
    public CanNotChangeCardStatusException() {
        super("Can not change status");
    }
}
