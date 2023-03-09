package ru.astondevs.asber.infoservice.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import ru.astondevs.asber.infoservice.dto.BankBranchDtoWithPagination;
import ru.astondevs.asber.infoservice.entity.BankBranch;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankBranchMapper {

    /**
     * Method that converts pagination to dto.
     * @param bankBranches transfer details with pagination
     * @return {@link BankBranchDtoWithPagination}
     */
    @Mapping(target = "currentPage", source = "pageable.pageNumber")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "bankBranches", source = "content")
    BankBranchDtoWithPagination toBankBranchDtoWithPagination(Page<BankBranch> bankBranches);
}