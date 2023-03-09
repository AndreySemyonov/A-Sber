package ru.astondevs.asber.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.moneytransferservice.dto.TransferTypeResponseDto;
import ru.astondevs.asber.moneytransferservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.moneytransferservice.mapper.TransferTypeMapper;
import ru.astondevs.asber.moneytransferservice.service.TransferTypeService;

/**
 * Controller that handles commission requests
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/commission")
@RequiredArgsConstructor
public class CommissionController implements CommissionControllerApi {

    private final TransferTypeService transferTypeService;

    private final TransferTypeMapper transferTypeMapper;

    /**
     * End point that provides commission info
     * @param typeName is a type name of a transfer (card or phone)
     * @param currencyCode is a currency code of sender's funds
     * @return {@link TransferTypeResponseDto} that contains info about commission
     */
    @Override
    @GetMapping
    public ResponseEntity<TransferTypeResponseDto> getCommissionResponse(
            @RequestParam String typeName,
            @RequestParam String currencyCode) {
        TransferTypeResponseDto response =  transferTypeMapper.transferTypeToTransferTypeResponseDto(
                transferTypeService.getTransferTypeByTransferTypeNameAndCurrencyFrom(typeName,
                        CurrencyCode.valueOf(currencyCode))
        );
        if (response.getCommissionFix() != null && response.getCommissionPercent() != null) {
            response.setCommissionFix(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
