package ru.astondevs.asber.userservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.astondevs.asber.userservice.service.impl.ContactsServiceImpl;
import ru.astondevs.asber.userservice.util.ErrorType;

import java.util.UUID;

/**
 * Class that throws exception if method {@link ContactsServiceImpl#changeEmailSubscriptionNotification(UUID, Boolean)} of
 * cannot find email.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends BaseUserServiceException {

    /**
     * Class constructor specifying messages and {@link ErrorType} for exception
     */
    public EmailNotFoundException() {
        super("Email not found", ErrorType.MAIL_NOT_FOUND);
    }
}
