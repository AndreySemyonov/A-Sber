package ru.astondevs.asber.moneytransferservice.service;

import org.springframework.data.domain.Page;
import ru.astondevs.asber.moneytransferservice.dto.TransferRequestDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatus;

import java.util.List;
import java.util.UUID;

/**
 * Service that works with {@link TransferDetails}.
 */
public interface TransferService {

    /**
     * Method that make new transfer.
     *
     * @param transferRequestDto transferRequestDto from {@link TransferRequestDto} with information about transfer
     * @return {@link TransferDetails}
     */
    TransferDetails newTransfer(TransferRequestDto transferRequestDto, UUID clientId);

    /**
     * Method that changes is_deleted status of {@link TransferDetails} to true.
     *
     * @param transferId from with information about transfer id.
     */
    void deleteDraftById(Long transferId);

    /**
     * Method that shows operations history
     *
     * @return list of {@link TransferDetails}
     */
    List<TransferDetails> getClientOperationsHistory(String clientId);

    /**
     * Method that updates transfer detail status
     *
     * @param transferId transfer id
     * @param status     {@link TransferStatus}
     * @return updated transfer details
     */
    TransferDetails updateStatus(Long transferId, TransferStatus status);

    /**
     * Method that add {@link TransferDetails} to favorites or delete from favorites
     *
     * @param transferId  Transfer id from {@link TransferDetails}
     * @param isFavourite Updating value
     * @return updated transfer details
     */
    TransferDetails changeIsFavouriteStatus(Long transferId, boolean isFavourite);

    /**
     * Method that gets list of operations by client id with pagination
     * @param clientId client id
     * @param pageNumber current page of pagination
     * @return List of operations with pagination
     */
    Page<TransferDetails> getClientOperationHistoryWithPagination(String clientId, Integer pageNumber);
}
