package ru.astondevs.asber.depositservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.depositservice.service.AgreementService;
import ru.astondevs.asber.depositservice.service.impl.AgreementServiceImpl;

import java.util.UUID;

/**
 * Class that throws exception if method {@link AgreementServiceImpl#changeAutoRenewalStatus(UUID, UUID, Boolean)}
 * of service {@link AgreementService} can not change renewal status.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotChangeRenewalStatusException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public CanNotChangeRenewalStatusException() {
        super("Can not change renewal status");
    }
}
