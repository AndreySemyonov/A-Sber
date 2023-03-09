package ru.astondevs.asber.infoservice.service;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import ru.astondevs.asber.infoservice.entity.BankBranch;
import ru.astondevs.asber.infoservice.repository.BankBranchRepository;
import ru.astondevs.asber.infoservice.service.impl.BankBranchServiceImpl;
import ru.astondevs.asber.infoservice.util.exception.InvalidPageIndexException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankBranchServiceTest {

    @InjectMocks
    private BankBranchServiceImpl bankBranchService;

    @Mock
    private BankBranchRepository bankBranchRepository;

    private final List<BankBranch> listBankBranch = new ArrayList<>();

    private Page <BankBranch> pageable;

    @BeforeEach
    void setUp() {
        BankBranch bankBranch1 = BankBranch.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .city("Moscow")
                .branchNumber("1")
                .build();

        BankBranch bankBranch2 = BankBranch.builder()
                .id(UUID.fromString("178a2322-86b7-11ed-a1eb-0242ac120002"))
                .city("Moscow")
                .branchNumber("2")
                .build();
        listBankBranch.add(bankBranch1);
        listBankBranch.add(bankBranch2);
        pageable = new PageImpl<>(listBankBranch);
    }




    @Test
    @DisplayName("if user wants to get bank branches then return them")
    void getBankBranches_shouldReturnBankBranches() {
        when(bankBranchRepository.findAll(PageRequest.of(0,20))).thenReturn(pageable);
        Page <BankBranch> resultListBankBranch = bankBranchService.getBankBranches(0);
        assertNotNull(resultListBankBranch);
        assertEquals(1, resultListBankBranch.getTotalPages());
        assertEquals("Moscow", resultListBankBranch.getContent().get(0).getCity());
    }





    @Test
    @DisplayName(" if user wants to get bank branches on the wrong page " +
            "- then return an error, with description")
    void getBankBranches_shouldReturnAnError() {
        when(bankBranchRepository.findAll((Pageable) any())).thenReturn(pageable);
        assertThrows(InvalidPageIndexException.class, () -> bankBranchService.getBankBranches(21));
    }

}
