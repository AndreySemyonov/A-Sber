package ru.astondevs.asber.infoservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Dto for pagination
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BankBranchDtoWithPagination {

    @Min(0)
    private Integer currentPage;

    private Integer totalPages;

    private List<BankBranchDto> bankBranches;
}
