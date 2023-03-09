package ru.astondevs.asber.moneytransferservice.controller;


import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatusDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.mapper.TransferMapper;
import ru.astondevs.asber.moneytransferservice.service.TransferService;

/**
 * Controller that handles request to {@link TransferService}
 */
@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class TransferDetailsController implements TransferDetailsControllerApi {

    private final TransferService transferService;

    private final TransferMapper transferMapper;

    /**
     * Endpoint that updates transfer or payment status to status provided in dto
     *
     * @param transferId transfer or payment id
     * @param dto {@link TransferStatusDto}
     * @return {@link TransferStatusDto}
     */
    @Override
    @PatchMapping("/{transferId}/status")
    public ResponseEntity<TransferStatusDto> updateTransferStatus(@PathVariable Long transferId,
                                                                     @Valid @RequestBody TransferStatusDto dto) {
        log.info("Request for transfer status update with id: {}", transferId);
        TransferDetails updatedTransferDetails = transferService.updateStatus(transferId,
            dto.getStatus());
        TransferStatusDto transferStatusDto = transferMapper.toTransferStatusDto(
            updatedTransferDetails);
        log.info("Returning updated transfer with status: {}", transferStatusDto.getStatus());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transferStatusDto);
    }
}
