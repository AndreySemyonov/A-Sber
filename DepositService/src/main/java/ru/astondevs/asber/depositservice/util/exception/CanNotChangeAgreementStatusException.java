package ru.astondevs.asber.depositservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.service.AgreementService;
import ru.astondevs.asber.depositservice.service.impl.AgreementServiceImpl;

import java.util.UUID;

/**
 * Class that throws exception if method {@link AgreementServiceImpl#revokeDeposit(UUID, AccountNumberDto)}
 * of service {@link AgreementService} can not change agreement.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotChangeAgreementStatusException extends RuntimeException {
    /**
     * Class constructor specifying messages for exception.
     */
    public CanNotChangeAgreementStatusException() {
        super("Can not change status");
    }
}
