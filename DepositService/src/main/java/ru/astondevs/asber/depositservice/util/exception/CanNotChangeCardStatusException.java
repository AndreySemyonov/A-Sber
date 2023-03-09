package ru.astondevs.asber.depositservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.depositservice.dto.CardStatusDto;
import ru.astondevs.asber.depositservice.service.CardService;
import ru.astondevs.asber.depositservice.service.impl.CardServiceImpl;

import java.util.UUID;

/**
 * Class that throws exception if method {@link CardServiceImpl#changeCardStatus(UUID, CardStatusDto)}
 * of service {@link CardService} can not change card status.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotChangeCardStatusException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public CanNotChangeCardStatusException() {
        super("Can not change status");
    }
}
