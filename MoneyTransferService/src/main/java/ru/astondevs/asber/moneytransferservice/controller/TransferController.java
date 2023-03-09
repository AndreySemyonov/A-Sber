package ru.astondevs.asber.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.moneytransferservice.dto.TransferIsFavouriteDto;
import ru.astondevs.asber.moneytransferservice.dto.TransferRequestDto;
import ru.astondevs.asber.moneytransferservice.dto.TransferResponseDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.mapper.TransferMapper;
import ru.astondevs.asber.moneytransferservice.service.TransferService;

import java.util.UUID;

/**
 * Controller that handles requests to {@link TransferService} and return response using {@link TransferMapper}.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class TransferController implements TransferControllerApi {

    private final TransferMapper transferMapper;
    private final TransferService transferService;

    /**
     * End-point that creates new transfer from received request body;
     *
     * @param transferRequestDto {@link TransferRequestDto} from request body with information about transfer
     */
    @Override
    @PostMapping("/new")
    public ResponseEntity<TransferResponseDto> newTransfer(@RequestParam UUID clientId,
                                                           @RequestBody TransferRequestDto transferRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transferMapper.transferToTransferResponseDto(transferService.newTransfer(transferRequestDto, clientId)));
    }

    /**
     * End-point that changes status of {@link TransferDetails}  to deleted
     *
     * @param transferId from request body with info about TransferDetails Id.
     */
    @Override
    @DeleteMapping("/{transferId}/draft")
    public ResponseEntity<Void> changeTransferStatusToDeleted(@PathVariable String transferId) {
        transferService.deleteDraftById(Long.valueOf(transferId));
        return ResponseEntity.ok().build();
    }

    /**
     * Request for transfer isFavourite status for client
     *
     * @param transferId  transfer identifier (required)
     * @param isFavourite is Favourite status for client (required)
     * @return {@link TransferIsFavouriteDto} that contains info about change status
     */
    @Override
    @PatchMapping("/{transferId}/favorites")
    public ResponseEntity<TransferIsFavouriteDto> changeIsFavouriteStatus(@PathVariable Long transferId,
                                                                          @RequestParam Boolean isFavourite) {
        log.info("Request for transfer isFavourite status: {}", isFavourite);
        TransferDetails transferDetails = transferService.changeIsFavouriteStatus(transferId, isFavourite);
        log.info("Response with transfer isFavourite status: {}", isFavourite);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transferMapper.transferToTransferIsFavouriteDto(transferDetails));
    }
}
