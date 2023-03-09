package ru.astondevs.asber.creditservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.astondevs.asber.creditservice.util.exception.CanNotChangeCardStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * RestControllerAdvice that handles all exception.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req,
        Exception e,
        ErrorType errorType) {
        Throwable rootCause = Optional.ofNullable(NestedExceptionUtils.getRootCause(e)).orElse(e);
        log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        return ResponseEntity.status(errorType.getStatus())
            .body(new ErrorInfo(req.getRequestURL(), errorType,
                e.getMessage(), LocalDateTime.now()));
    }

    /**
     * Method that handles CanNotChangeCardStatusException.
     *
     * @param req request http information
     * @param ex  {@link CanNotChangeCardStatusException}
     * @return custom response with message exception
     */
    @ExceptionHandler(CanNotChangeCardStatusException.class)
    public ResponseEntity<ErrorInfo> handleCanNotChangeCardStatusException(HttpServletRequest req,
        CanNotChangeCardStatusException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }

    /**
     * Method that handles EntityNotFoundException.
     *
     * @param req request http information
     * @param ex  {@link EntityNotFoundException}
     * @return custom response with message exception
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEntityNotFoundException(HttpServletRequest req,
        EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.ENTITY_NOT_FOUND);
    }
}
