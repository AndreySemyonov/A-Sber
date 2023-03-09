package ru.astondevs.asber.infoservice.util.exception;

/**
 * Will be thrown if there are more than the total number of pages or less than null {@link ru.astondevs.asber.infoservice.service.impl.BankBranchServiceImpl#{getBankBranches()}
 */

public class InvalidPageIndexException extends RuntimeException{
    public InvalidPageIndexException() {
        super("Invalid page index");
    }
}
