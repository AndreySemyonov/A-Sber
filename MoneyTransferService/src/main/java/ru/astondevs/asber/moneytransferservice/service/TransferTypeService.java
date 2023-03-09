package ru.astondevs.asber.moneytransferservice.service;

import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;

/**
 * Service that works with {@link TransferType}.
 */
public interface TransferTypeService {

    /**
     * Method that gets TransferType by id.
     * @param id Transfer Type id from {@link TransferType}
     * @return {@link TransferType}
     */
    TransferType getTransferTypeById(Long id);

    /**
     * Method that gets transfer type {@link TransferType} by transfer type name and sender's currency
     * @param transferTypeName is transfer type name of {@link TransferType}
     * @param currencyFrom is currency that will be debited from the sender
     * @return {@link TransferType}
     */
    TransferType getTransferTypeByTransferTypeNameAndCurrencyFrom(String transferTypeName, CurrencyCode currencyFrom);
}
