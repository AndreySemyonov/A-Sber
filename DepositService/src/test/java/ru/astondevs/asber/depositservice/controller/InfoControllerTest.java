package ru.astondevs.asber.depositservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.astondevs.asber.depositservice.ExternalSystemConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {ExternalSystemConfig.PostgresInitializer.class,
        ExternalSystemConfig.KafkaInitializer.class})
class InfoControllerTest {
    private final String URL = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("If client requests products then return active products")
    void getProducts_shouldReturnDtoProducts() throws Exception {
        final String getProductsUrl = String.format("%s/products", URL);

        mockMvc.perform(get(getProductsUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(3))
                .andDo(print());
    }

    @Test
    @DisplayName("If client requests card products then return active card products")
    void getCardProducts_shouldReturnDtoProducts() throws Exception {
        final String getCardProductsUrl = String.format("%s/card-products", URL);

        mockMvc.perform(get(getCardProductsUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(3))
                .andDo(print());
    }
}