package ru.astondevs.asber.infoservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.infoservice.dto.BankBranchDtoWithPagination;
import ru.astondevs.asber.infoservice.entity.BankBranch;
import ru.astondevs.asber.infoservice.repository.mapper.BankBranchMapper;
import ru.astondevs.asber.infoservice.service.BankBranchService;


/**
 * Controller for bank-branches requests
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BankBranchesController {

    private final BankBranchService bankBranchService;

    private final BankBranchMapper bankBranchMapper;

    /**
     * Endpoint for getting pages of bank-branches
     */
    @PostMapping("/bank-branch")
    public BankBranchDtoWithPagination getBankBranches(@RequestParam Integer page) {
        log.info("Request for list of bank branches");
        Page<BankBranch> bankBranches = bankBranchService.getBankBranches(page);
        BankBranchDtoWithPagination bankBranchDtoWithPagination = bankBranchMapper.toBankBranchDtoWithPagination(bankBranches);
        log.info("Returning list of bank branches");
        return bankBranchDtoWithPagination;
    }
}
