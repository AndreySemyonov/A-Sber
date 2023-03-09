package ru.astondevs.asber.creditservice.util;

import java.time.LocalDateTime;

/**
 * CLass that stores error information.
 */

public record ErrorInfo(StringBuffer url, ErrorType type, String exceptionMsg, LocalDateTime timeStamp) {

}
