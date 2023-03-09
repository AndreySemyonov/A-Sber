package ru.astondevs.asber.userservice.util;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.astondevs.asber.userservice.util.exception.BaseUserServiceException;
import ru.astondevs.asber.userservice.util.exception.ClientBlockedException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * RestControllerAdvice that handles all exception.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Method that handles exception when entity wasn't found
     *
     * @param req request {@link HttpServletRequest}
     * @param ex  exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.ENTITY_NOT_FOUND);
    }

    /**
     * Method that handles application exceptions
     *
     * @param req request {@link HttpServletRequest}
     * @param ex  exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleAppException(HttpServletRequest req, Exception ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.APP_ERROR);
    }

    /**
     * Method that handles validation exception
     *
     * @param req request {@link HttpServletRequest}
     * @param ex  exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.VALIDATION_EXCEPTION);
    }

    /**
     * Method that handles exception when client is blocked
     *
     * @param ex exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ClientBlockedException.class)
    public ResponseEntity<ClientBlockedException> handleClientBlockedException(ClientBlockedException ex) {
        return ResponseEntity.status(ErrorType.CLIENT_BLOCKED_EXCEPTION.getStatus()).body(ex);
    }

    /**
     * Method that handles malformed JWT exception
     *
     * @param req request {@link HttpServletRequest}
     * @param ex  exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorInfo> handleMalformedJwtExceptionException(HttpServletRequest req, MalformedJwtException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.INVALID_JWT);
    }

    /**
     * Method that handles custom exceptions
     * @param req is request
     * @param ex is exception
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(BaseUserServiceException.class)
    public ResponseEntity<ErrorInfo> handleUserServiceException(HttpServletRequest req, BaseUserServiceException ex) {
        return logAndGetErrorInfo(req, ex, ex.getErrorType());
    }

    /**
     * Method that handles custom exceptions
     * @param req is request
     * @param ex is exception
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.CONSTRAINT_VIOLATION);
    }

    /**
     * Method that handles log and get exception
     *
     * @param req request {@link HttpServletRequest}
     * @return {@link ResponseEntity}
     */
    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType) {
        Throwable rootCause = Optional.ofNullable(NestedExceptionUtils.getRootCause(e)).orElse(e);
        log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        return ResponseEntity.status(errorType.getStatus())
                .body(new ErrorInfo("/user" + req.getRequestURI(), errorType,
                        e.getMessage(), LocalDateTime.now())
                );
    }
}
