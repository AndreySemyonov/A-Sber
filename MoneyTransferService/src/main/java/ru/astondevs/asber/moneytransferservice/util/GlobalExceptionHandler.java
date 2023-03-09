package ru.astondevs.asber.moneytransferservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.astondevs.asber.moneytransferservice.util.exception.CommissionIsNotDefinedInDbException;
import ru.astondevs.asber.moneytransferservice.util.exception.ExternalServiceRequestException;
import ru.astondevs.asber.moneytransferservice.util.exception.NotFoundException;
import ru.astondevs.asber.moneytransferservice.util.exception.WrongPageIndexException;
import ru.astondevs.asber.moneytransferservice.util.exception.WrongStatusException;
import ru.astondevs.asber.moneytransferservice.dto.ErrorInfo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

/**
 * RestControllerAdvice that handles all exception.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e,
                                                         HttpStatus status) {
        Throwable rootCause = Optional.ofNullable(NestedExceptionUtils.getRootCause(e)).orElse(e);
        log.error(status + " at request " + req.getRequestURL(), rootCause);
        return ResponseEntity.status(status)
                .body(new ErrorInfo(req.getRequestURL().toString(), status.toString(),
                        e.getMessage(), LocalDateTime.now()));
    }

    /**
     * Method that handles NotFoundException.
     *
     * @param req request http information
     * @param ex  {@link NotFoundException}
     * @return custom response with message exception
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleCanNotFindCardNumberException(HttpServletRequest req,
                                                                         NotFoundException ex) {
        return logAndGetErrorInfo(req, ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Method that handles ExternalServiceRequestException.
     *
     * @param req request http information
     * @param ex  {@link ExternalServiceRequestException}
     * @return custom response with message exception
     */
    @ExceptionHandler(ExternalServiceRequestException.class)
    public ResponseEntity<ErrorInfo> handleCanNotFindCardNumberBadRequestException(
            HttpServletRequest req, ExternalServiceRequestException ex
    ) {
        return logAndGetErrorInfo(req, ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEntityNotFoundException(HttpServletRequest req,
                                                                   EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Method that handles WrongStatusException.
     *
     * @param req request http information
     * @param ex  {@link ExternalServiceRequestException}
     * @return custom response with message exception
     */
    @ExceptionHandler(WrongStatusException.class)
    public ResponseEntity<ErrorInfo> handleWrongStatusException(
            HttpServletRequest req, ExternalServiceRequestException ex
    ) {
        return logAndGetErrorInfo(req, ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method that handles CommissionIsNotDefinedInDbException.
     *
     * @param req request http information
     * @param ex  {@link CommissionIsNotDefinedInDbException}
     * @return custom response with message exception
     */
    @ExceptionHandler(CommissionIsNotDefinedInDbException.class)
    public ResponseEntity<ErrorInfo> handleCommissionIsNotDefinedInDbException(
            HttpServletRequest req, ExternalServiceRequestException ex
    ) {
        return logAndGetErrorInfo(req, ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WrongPageIndexException.class)
    public ResponseEntity<ErrorInfo> handleWrongPageIndexException(
        HttpServletRequest req, ExternalServiceRequestException ex
    ) {
        return logAndGetErrorInfo(req, ex, HttpStatus.BAD_REQUEST);
    }
}


