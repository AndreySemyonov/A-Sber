package ru.astondevs.asber.moneytransferservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.moneytransferservice.repository.TransferTypeRepository;
import ru.astondevs.asber.moneytransferservice.service.TransferTypeService;
import ru.astondevs.asber.moneytransferservice.util.exception.CommissionIsNotDefinedInDbException;

import javax.persistence.EntityNotFoundException;

/**
 * Implementation of {@link TransferTypeService}.
 * Works with {@link TransferTypeRepository}.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferTypeServiceImpl implements TransferTypeService {

    private final TransferTypeRepository transferTypeRepository;

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link TransferTypeRepository#findTransferTypeByTransferTypeId(Long)}.
     * @param id TransferType transferTypeId from {@link TransferType}
     * @return {@link TransferType}
     * @throws EntityNotFoundException if transfer type not found
     */
    @Override
    public TransferType getTransferTypeById(Long id) {
        return transferTypeRepository.findTransferTypeByTransferTypeId(id)
                .orElseThrow(() -> new EntityNotFoundException("Transfer Type not found by id = " + id));
    }

    /**
     * Method that gets transfer type {@link TransferType} by transfer type name and sender's currency
     * @param transferTypeName is transfer type name of {@link TransferType}
     * @param currencyFrom is currency that will be debited from the sender
     * @return {@link TransferType}
     */
    @Override
    public TransferType getTransferTypeByTransferTypeNameAndCurrencyFrom(String transferTypeName, CurrencyCode currencyFrom) {
        TransferType transferType = transferTypeRepository
                .findTransferTypeByTransferTypeNameAndCurrencyFrom(transferTypeName, currencyFrom)
                .orElseThrow(() -> new EntityNotFoundException("Transfer type is not found"));
        if (transferType.getCommissionFix() == null && transferType.getCommissionPercent() == null) {
            throw new CommissionIsNotDefinedInDbException();
        }
        return transferType;
    }
}
