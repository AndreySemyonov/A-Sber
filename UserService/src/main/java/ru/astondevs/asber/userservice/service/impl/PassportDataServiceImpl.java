package ru.astondevs.asber.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.PassportData;
import ru.astondevs.asber.userservice.repository.PassportDataRepository;
import ru.astondevs.asber.userservice.service.PassportDataService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link PassportDataService}.
 * Works with {@link PassportDataRepository}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PassportDataServiceImpl implements PassportDataService {
    private final PassportDataRepository passportDataRepository;

    /**
     * {@inheritDoc}
     * For saving passport data uses method {@link PassportDataRepository#save(Object)}.
     *
     * @param passportData from {@link PassportData}
     * @param client       from {@link Client}
     * @return {@link PassportData}
     */
    @Override
    @Transactional
    public PassportData save(PassportData passportData, Client client) {
        log.info("Saving client: {}", client.getId());
        passportData.setClient(client);

        log.info("Saved client: {}", client.getId());
        return passportDataRepository.save(passportData);
    }

    /**
     * {@inheritDoc}
     * For getting client id from db by passport number
     * uses method {@link PassportDataRepository#findPassportDataByIdentificationPassportNumber(String)}.
     * @param passportNumber identification passport number from {@link PassportData}.
     * @return {@link UUID}.
     * @throws EntityNotFoundException if client not found.
     */
    @Override
    public UUID getClientIdByPassportNumber(String passportNumber) {
        Optional<PassportData> optionalData =
                passportDataRepository.findPassportDataByIdentificationPassportNumber(passportNumber);
        if (optionalData.isPresent()) {
            log.info("Returning client id with passport number: {}", optionalData.get().getIdentificationPassportNumber());
            return optionalData.get().getClient().getId();
        } else {
            log.error("No client with passport number: {}", passportNumber);
            throw new EntityNotFoundException();
        }
    }
}
