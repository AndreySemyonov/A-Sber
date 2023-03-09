package ru.astondevs.asber.creditservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.repository.AgreementRepository;
import ru.astondevs.asber.creditservice.service.AgreementService;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;

/**
 * Implementation of {@link AgreementService}. Works with {@link AgreementRepository} and
 * {@link Agreement}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link AgreementRepository#findAgreementByCreditId(UUID)}.
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Agreement}
     * @throws EntityNotFoundException if agreement not found
     */
    @Override
    public Agreement getAgreementByCreditId(UUID creditId) {
        log.info("Request for agreement with credit id: {}", creditId);
        Optional<Agreement> optionalAgreement = agreementRepository.findAgreementByCreditId(
            creditId);
        if (optionalAgreement.isPresent()) {
            log.info("Returning agreement with agreement id: {}", optionalAgreement.get().getId());
            return optionalAgreement.get();
        } else {
            log.error("There is no agreement entity with this credit id: {}", creditId);
            throw new EntityNotFoundException();
        }
    }
}
