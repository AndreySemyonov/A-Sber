package ru.astondevs.asber.infoservice.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.astondevs.asber.infoservice.ExternalSystemConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
public class BankBranchesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String BANK_BRANCH_URL = "/api/v1/bank-branch";

    @Test
    @DisplayName("If user wants to get bank branches then return them")
    void getBankBranches_shouldReturnBankBranches() throws Exception {
        mockMvc.perform(post(BANK_BRANCH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0"))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Check wrong number of page")
    void getBankBranches_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post(BANK_BRANCH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "3"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}

