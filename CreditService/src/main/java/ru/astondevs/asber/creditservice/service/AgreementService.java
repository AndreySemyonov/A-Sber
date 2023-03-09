package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.Credit;

import java.util.UUID;

/**
 * Service that works with {@link Agreement}.
 */
public interface AgreementService {

    /**
     * Method that gets agreement by credit id.
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Agreement}
     */
    Agreement getAgreementByCreditId(UUID creditId);

}
