package ru.astondevs.asber.creditservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.creditservice.ExternalSystemConfig;
import ru.astondevs.asber.creditservice.entity.Product;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("If user wants to get list of products by is active field(true) then return list of products")
    void findProductsByIsActive_shouldReturnListOfProducts() {
        List<Product> productsByIsActive = productRepository.findProductsByIsActive(true);
        assertEquals(3, productsByIsActive.size());
    }

    @Test
    @DisplayName("If user wants to get list of products by is active field(false) then return empty list")
    void findProductsByIsActive_shouldReturnEmptyList() {
        List<Product> productsByIsActive = productRepository.findProductsByIsActive(false);
        assertEquals(0, productsByIsActive.size());
    }
}