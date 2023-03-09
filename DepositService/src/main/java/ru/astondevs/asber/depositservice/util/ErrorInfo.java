package ru.astondevs.asber.depositservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CLass that stores error information.
 */
@AllArgsConstructor
@Getter
public class ErrorInfo {
    private final StringBuffer url;
    private final ErrorType type;
    private final String exceptionMsg;
    private final LocalDateTime timeStamp;
}
