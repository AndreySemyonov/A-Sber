package ru.astondevs.asber.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.moneytransferservice.dto.OperationsHistoryDtoWithPagination;
import ru.astondevs.asber.moneytransferservice.dto.TransferDetailsDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;
import ru.astondevs.asber.moneytransferservice.mapper.OperationsHistoryMapper;
import ru.astondevs.asber.moneytransferservice.service.impl.TransferServiceImpl;

/**
 * Controller that handles history of operations
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class OperationsHistoryController implements OperationsHistoryControllerApi {

    private final TransferServiceImpl transferService;
    private final OperationsHistoryMapper operationsHistoryMapper;

    /**
     * Getting the history of operations client by client id.
     *
     * @param clientId client identifier (required)
     * @param page     Number of page
     * @return {@link TransferDetailsDto} that contains info about history of operations
     */
    @Override
    @GetMapping
    public ResponseEntity<OperationsHistoryDtoWithPagination> showOperationsHistory(String clientId,
        Integer page) {
        log.info("Request for operations history for clientId: {}", clientId);
        Page<TransferDetails> clientOperationHistoryWithPagination = transferService.getClientOperationHistoryWithPagination(
            clientId, page);
        OperationsHistoryDtoWithPagination dtoWithPagination = operationsHistoryMapper.toOperationHistoryWithPagination(
            clientOperationHistoryWithPagination);
        log.info("Returning operations history for clientId: {}", clientId);
        return ResponseEntity.ok(dtoWithPagination);
    }
}
