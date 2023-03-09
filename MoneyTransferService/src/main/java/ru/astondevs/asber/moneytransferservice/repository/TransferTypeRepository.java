package ru.astondevs.asber.moneytransferservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;

import java.util.Optional;

/**
 * Repository that stores {@link TransferType} entities.
 */
public interface TransferTypeRepository extends JpaRepository<TransferType, Long> {

    /**
     * Method that finds TransferType, having transfer id.
     * @param id Transfer Type id from {@link TransferType}
     * @return {@link Optional} wrap of {@link TransferType}
     */
    Optional<TransferType> findTransferTypeByTransferTypeId(Long id);

    /**
     * Method that gets transfer type {@link TransferType} by transfer type name and sender's currency
     * @param transferTypeName is transfer type name of {@link TransferType}
     * @param currencyFrom is currency that will be debited from the sender
     * @return {@link Optional} of {@link TransferType}
     */
    Optional<TransferType> findTransferTypeByTransferTypeNameAndCurrencyFrom(String transferTypeName, CurrencyCode currencyFrom);

}
