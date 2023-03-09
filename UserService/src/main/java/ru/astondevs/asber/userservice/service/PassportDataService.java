package ru.astondevs.asber.userservice.service;

import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.PassportData;

import java.util.UUID;


/**
 * Service that works with {@link PassportData}.
 */
public interface PassportDataService {
    /**
     * Method that saves passport data
     *
     * @param passportData from {@link PassportData}
     * @param client       from {@link Client}
     * @return {@link PassportData}
     */
    PassportData save(PassportData passportData, Client client);

    /**
     * For getting client id from db by passport number
     *
     * @param passportNumber identification passport number from {@link PassportData}.
     * @return {@link UUID}.
     */
    UUID getClientIdByPassportNumber(String passportNumber);
}
