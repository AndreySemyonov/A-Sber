package ru.astondevs.asber.moneytransferservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.moneytransferservice.dto.TransferRequestDto;
import ru.astondevs.asber.moneytransferservice.entity.CardTransfer;
import ru.astondevs.asber.moneytransferservice.entity.PhoneTransfer;
import ru.astondevs.asber.moneytransferservice.entity.Transfer;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;
import ru.astondevs.asber.moneytransferservice.mapper.TransferMapper;
import ru.astondevs.asber.moneytransferservice.repository.TransferDetailsRepository;
import ru.astondevs.asber.moneytransferservice.service.TransferService;
import ru.astondevs.asber.moneytransferservice.service.TransferTypeService;
import ru.astondevs.asber.moneytransferservice.util.exception.InvalidUuidException;
import ru.astondevs.asber.moneytransferservice.util.exception.WrongPageIndexException;
import ru.astondevs.asber.moneytransferservice.util.exception.WrongStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

/**
 * Implementation of {@link TransferService}.
 * Works with {@link TransferDetailsRepository} and {@link TransferTypeService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferServiceImpl implements TransferService {

    private final TransferMapper transferMapper;
    private final TransferDetailsRepository transferDetailsRepository;
    private final TransferTypeService transferTypeService;

    /**
     * {@inheritDoc}
     * For getting data about TransferType from db uses method {@link TransferTypeService#getTransferTypeById(Long)}.
     * For saving data about TransferDetails in db.
     *
     * @param transferRequestDto TransferRequestDto  from {@link TransferRequestDto} with transfer information.
     * @return {@link TransferDetails}
     * @throws EntityNotFoundException if TransferType not found
     */
    @Transactional
    @Override
    public TransferDetails newTransfer(TransferRequestDto transferRequestDto, UUID clientId) {
        TransferDetails transfer = transferMapper.transferFromTransferRequestDto(transferRequestDto);
        TransferType transferType = transferTypeService.getTransferTypeById(transferRequestDto.getTransferTypeId());

        transfer.setSenderId(clientId);
        //TODO receiverId может быть null, т.к. транзакция может быть выполнена между разными банками, пока сохраняем null в бд
        transfer.setReceiverId(null);
        transfer.setTransferTypeId(transferType);
        transfer.setTransferExchSum(transferRequestDto.getSum());
        //TODO currencyFrom, currencyTo пока что всегда RUB, т.к. в DTO не приходит информация о валютах
        transfer.setCurrencyFrom(CurrencyCode.RUB);
        transfer.setCurrencyTo(CurrencyCode.RUB);
        //TODO transferSystemId пока что null, т.к. в DTO не приходит информация о платёжной системе
        transfer.setTransferSystemId(null);
        //TODO если periodicity не null, считаем, что это автоплатёж, устанавливаем флаг isAuto = true
        if (transferRequestDto.getPeriodicity() != null) {
            transfer.setIsAuto(true);
            transfer.setExpirationDate(transferRequestDto.getStartDate().plusMonths(transferRequestDto.getPeriodicity()));
        } else {
            transfer.setIsAuto(false);
            transfer.setExpirationDate(null);
        }
        //TODO authorizationCode пока что null, исправить при подключении АБС к проекту
        transfer.setAuthorizationCode(null);
        transfer.setStatus(TransferStatus.DRAFT);
        transfer.setIsDeleted(false);
        transfer.setTransfer(saveTransferByPhonePresence(transferRequestDto, transfer));
        transferDetailsRepository.save(transfer);
        return transfer;
    }

    /**
     * {@inheritDoc}
     * For getting data about Transfer operations. Finding client operations by Client ID.
     *
     * @return list of {@link TransferDetails}
     * @throws InvalidUuidException if UUID string is not valid
     */
    public List<TransferDetails> getClientOperationsHistory(String clientId) {
        log.info("Request operations history for clientId: {}", clientId);

        List<TransferDetails> operations;
        try {
            UUID senderId = UUID.fromString(clientId);
            operations = transferDetailsRepository.findAllBySenderIdOrderByStartDateDesc(senderId);
        } catch (IllegalArgumentException exception) {
            log.error("Invalid clientId");
            throw new InvalidUuidException();
        }

        log.info("Operations history received for clientId: {}", clientId);
        return operations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TransferDetails updateStatus(Long transferId, TransferStatus status) {
        log.info("Request for status update for transfer with id {}", transferId);
        Optional<TransferDetails> optionalTransferDetails = transferDetailsRepository.findById(
                transferId);
        if (optionalTransferDetails.isEmpty()) {
            log.error("Transfer with id {} is not found", transferId);
            throw new EntityNotFoundException();
        }
        TransferDetails transferDetails = optionalTransferDetails.get();
        transferDetails.setStatus(status);
        log.info("Updated status to {} for transfer with id {}", status, transferId);
        return transferDetailsRepository.save(transferDetails);
    }

    /**
     * {@inheritDoc}
     * For making PhoneTransfer or CardTransfer by Transfer.
     *
     * @param transferRequestDto TransferRequestDto from {@link TransferRequestDto} with transfer information
     * @param transfer           Transfer from {@link Transfer} with entity.
     * @return {@link Transfer}
     */
    private Transfer saveTransferByPhonePresence(TransferRequestDto transferRequestDto, TransferDetails transfer) {
        if (transferRequestDto.getMobilePhone() != null) {
            PhoneTransfer phoneTransfer = transferMapper.phoneTransferFromTransferRequestDto(transferRequestDto);
            phoneTransfer.setTransferDetails(transfer);
            //TODO phone_code пока может быть null
            phoneTransfer.setPhoneCode(null);
            return phoneTransfer;
        } else {
            CardTransfer cardTransfer = transferMapper.cardTransferFromTransferRequestDto(transferRequestDto);
            cardTransfer.setTransferDetails(transfer);
            return cardTransfer;
        }
    }

    /**
     * Method that changes is_deleted status of {@link TransferDetails} to true.
     *
     * @param transferId from with information about transfer id.
     */
    @Override
    public void deleteDraftById(Long transferId) {
        TransferDetails transferDetails = transferDetailsRepository.findById(transferId).orElseThrow(() -> {
            log.error("No transfer with Id: {}", transferId);
            return new EntityNotFoundException("Transfer is not found");
        });
        if (transferDetails.getStatus() != TransferStatus.DRAFT) {
            throw new WrongStatusException();
        } else {
            transferDetails.setIsDeleted(true);
            transferDetailsRepository.save(transferDetails);
        }
    }

    /**
     * {@inheritDoc}
     * For updates data in db uses method {@link TransferDetailsRepository#save(Object)}.
     * Before this getting transfer details uses {@link TransferDetailsRepository#findById(Long)}
     *
     * @param transferId  Transfer id from {@link TransferDetails}
     * @param isFavourite Updating value
     * @return {@link TransferDetails}
     */
    @Transactional
    @Override
    public TransferDetails changeIsFavouriteStatus(Long transferId, boolean isFavourite) {
        log.info("Request for transfer details with transfer id: {}", transferId);
        TransferDetails transferDetails = transferDetailsRepository.findById(transferId)
                .orElseThrow(EntityNotFoundException::new);
        if (transferDetails.getIsFavourite().equals(isFavourite)) {
            log.info("Returning transfer details without changing the isFavourite status");
            return transferDetails;
        } else {
            log.info("Request for changing isFavourite status");
            transferDetails.setIsFavourite(isFavourite);
            transferDetailsRepository.save(transferDetails);
        }
        log.info("Returning transfer details with new isFavourite status");
        return transferDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Page<TransferDetails> getClientOperationHistoryWithPagination(String clientId,
        Integer pageNumber) {
        log.info("Request operations history for clientId: {}", clientId);
        Pageable pageRequest = PageRequest.of(pageNumber, 20);
        Page<TransferDetails> operationWithPagination = transferDetailsRepository.findAllBySenderIdOrderByStartDateDesc(
            UUID.fromString(clientId), pageRequest);
        int totalPages = operationWithPagination.getTotalPages();
        if (pageNumber >= totalPages || pageNumber < 0) {
            log.error("Invalid page number");
            throw new WrongPageIndexException();
        }
        log.info("Operations history received for clientId: {}", clientId);
        return operationWithPagination;
    }

}
