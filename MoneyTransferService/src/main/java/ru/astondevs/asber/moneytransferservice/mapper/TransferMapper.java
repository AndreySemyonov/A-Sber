package ru.astondevs.asber.moneytransferservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.moneytransferservice.dto.TransferIsFavouriteDto;
import ru.astondevs.asber.moneytransferservice.dto.TransferRequestDto;
import ru.astondevs.asber.moneytransferservice.dto.TransferResponseDto;
import ru.astondevs.asber.moneytransferservice.dto.TransferStatusDto;
import ru.astondevs.asber.moneytransferservice.entity.CardTransfer;
import ru.astondevs.asber.moneytransferservice.entity.PhoneTransfer;
import ru.astondevs.asber.moneytransferservice.entity.Transfer;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;

/**
 * Mapper that converts entities {@link Transfer},{@link TransferDetails} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransferMapper {

    /**
     * Method that converts transferRequestDto to TransferDetails for new transfer.
     * @param transferRequestDto {@link TransferRequestDto}
     * @return {@link TransferDetails} with transfer information
     */
    @Mapping(target = "transferSum", source = "sum")
    @Mapping(target = "commission", source = "sumCommission")
    @Mapping(target = "isFavourite", source = "isFavourite")
    @Mapping(target = "periodicity", source = "periodicity")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "transferDate", source = "startDate")
    @Mapping(target = "purposeOfTransfer", source = "purpose")
    @Mapping(target = "exchangeRate", source = "currencyExchange")
    @Mapping(target = "transferTypeId", ignore = true)
    TransferDetails transferFromTransferRequestDto(TransferRequestDto transferRequestDto);

    /**
     * Method that converts transferRequestDto to PhoneTransfer for new transfer.
     * @param transferRequestDto {@link TransferRequestDto}
     * @return {@link PhoneTransfer} with phone-transfer information
     */
    @Mapping(target = "senderCardNumber", source = "remitterCardNumber")
    @Mapping(target = "receiverPhone", source = "mobilePhone")
    @Mapping(target = "receiverCardNumber", source = "payeeCardNumber")
    PhoneTransfer phoneTransferFromTransferRequestDto(TransferRequestDto transferRequestDto);

    /**
     * Method that converts transferRequestDto to CardTransfer for new transfer.
     * @param transferRequestDto {@link TransferRequestDto}
     * @return {@link CardTransfer} with card-transfer information
     */
    @Mapping(target = "senderCardNumber", source = "remitterCardNumber")
    @Mapping(target = "receiverCardNumber", source = "payeeCardNumber")
    CardTransfer cardTransferFromTransferRequestDto(TransferRequestDto transferRequestDto);

    /**
     * Method that converts TransferDetails to TransferResponseDto for new transfer.
     * @param transferDetails {@link TransferDetails}
     * @return {@link TransferResponseDto} with transfer information
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "startDate")
    @Mapping(target = "status", source = "status")
    TransferResponseDto transferToTransferResponseDto(TransferDetails transferDetails);

    /**
     * Method that converts {@link TransferDetails} to {@link TransferStatusDto}
     * @param transferDetails {@link TransferDetails}
     * @return {@link TransferStatusDto}
     */
    @Mapping(target = "transferId", source = "id")
    @Mapping(target = "status", source = "status")
    TransferStatusDto toTransferStatusDto(TransferDetails transferDetails);

    /**
     * method that converts {@link TransferDetails} to {@link TransferIsFavouriteDto}
     * @param transferDetails {@link TransferDetails}
     * @return {@link TransferIsFavouriteDto}
     */
    @Mapping(target = "isFavourite", source = "isFavourite")
    TransferIsFavouriteDto transferToTransferIsFavouriteDto(TransferDetails transferDetails);
}
