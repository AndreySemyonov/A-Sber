package ru.astondevs.asber.moneytransferservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.moneytransferservice.dto.TransferTypeResponseDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferType;

/**
 * Mapper that converts {@link TransferType} to data transfer objects
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransferTypeMapper {

    /**
     * Method that converts {@link TransferType} to {@link TransferTypeResponseDto}
     * @param transferType is {@link TransferType}
     * @return {@link TransferTypeResponseDto}
     */
    TransferTypeResponseDto transferTypeToTransferTypeResponseDto(TransferType transferType);
}
