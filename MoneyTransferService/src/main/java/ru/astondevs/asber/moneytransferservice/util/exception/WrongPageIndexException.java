package ru.astondevs.asber.moneytransferservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongPageIndexException extends RuntimeException {

    public WrongPageIndexException() {
        super("Wrong page index, it should be above zero and below total page count");
    }

}
