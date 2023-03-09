package ru.astondevs.asber.infoservice.util;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.astondevs.asber.infoservice.dto.ErrorInfo;
import ru.astondevs.asber.infoservice.util.exception.InvalidPageIndexException;

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEntityNotFoundException(HttpServletRequest req,
        EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Method that handles InvalidPageIndexException.
     *
     * @param req request http information
     * @param ex  {@link InvalidPageIndexException}
     * @return custom response with message exception
     */

    @ExceptionHandler(InvalidPageIndexException.class)
    public ResponseEntity<ErrorInfo> handleInvalidPageException(HttpServletRequest req,
                                                                InvalidPageIndexException ex) {
        return logAndGetErrorInfo(req, ex, HttpStatus.BAD_REQUEST);
    }
}
