package ru.astondevs.asber.moneytransferservice.controller;

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
import ru.astondevs.asber.moneytransferservice.ExternalSystemConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class OperationsHistoryControllerTest {

    private final String URL = "/api/v1/history";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Check operations history by valid clientId")
    void checkHistoryByValidId_shouldReturnListOfOperations() throws Exception {
        final String clientId = "38dca868-5eaa-11ed-9b6a-0242ac120002";

        mockMvc.perform(get(URL)
                        .param("clientId", clientId)
                        .param("page", String.valueOf(0))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Check operations history by valid clientId with wrong page index")
    void checkHistoryByValidIdWithWrongPageIndex_shouldThrowBadRequest() throws Exception {
        final String clientId = "38dca868-5eaa-11ed-9b6a-0242ac120002";

        mockMvc.perform(get(URL)
                .param("clientId", clientId)
                .param("page", String.valueOf(15))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
    @Test
    @DisplayName("Check operations history by invalid clientId")
    void checkHistoryByInvalidId_shouldReturnBadRequest() throws Exception {
        final String clientId = "abc";

        mockMvc.perform(get(URL)
                        .param("clientId", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Check operations history without providing clientId")
    void checkHistoryByEmptyId_shouldReturnBadRequest() throws Exception {
        final String clientId = null;

        mockMvc.perform(get(URL)
                        .param("clientId", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}