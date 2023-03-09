package ru.astondevs.asber.moneytransferservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import ru.astondevs.asber.moneytransferservice.dto.OperationsHistoryDtoWithPagination;
import ru.astondevs.asber.moneytransferservice.dto.TransferDetailsDto;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;

import java.util.List;

/**
 * Mapper that converts entities {@link TransferDetails} to list of transfer details objects DTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperationsHistoryMapper {

    /**
     * Method that converts transferDetails to TransferDetailsDto.
     * @param transferDetails {@link TransferDetails}
     * @return {@link TransferDetailsDto} with transfer information
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "startDate")
    @Mapping(target = "purpose", source = "purposeOfTransfer")
    @Mapping(target = "sum", source = "transferSum")
    @Mapping(target = "completedAt", source = "transferDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "currencyCode", source = "currencyTo")
    @Mapping(target = "typeName", source = "transferTypeId.transferTypeName")
    TransferDetailsDto toTransferDetailsDto(TransferDetails transferDetails);

    /**
     * Method that converts transferDetails list to TransferDetailsDto list.
     * @param transferDetails {@link List<TransferDetails>}
     * @return {@link List<TransferDetailsDto>} with transfer information
     */
    List<TransferDetailsDto> toTransferDetailsDtoList(List<TransferDetails> transferDetails);

    /**
     * Method that converts pagination to dto.
     * @param pagination transfer details with pagination
     * @return {@link OperationsHistoryDtoWithPagination}
     */
    @Mapping(target = "operations", source = "content")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "currentPage", source = "pageable.pageNumber")
    OperationsHistoryDtoWithPagination toOperationHistoryWithPagination(Page<TransferDetails> pagination);
}
