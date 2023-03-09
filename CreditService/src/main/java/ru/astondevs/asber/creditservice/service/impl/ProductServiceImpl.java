package ru.astondevs.asber.creditservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.creditservice.entity.Product;
import ru.astondevs.asber.creditservice.repository.ProductRepository;
import ru.astondevs.asber.creditservice.service.ProductService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ProductService}. Works with {@link ProductRepository} and
 * {@link Product}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link ProductRepository#findProductsByIsActive(Boolean)}.
     *
     * @return {@link List} of {@link Product}
     */
    @Override
    public List<Product> getActiveProducts() {
        log.info("Request for active products list");
        List<Product> activeProductList = productRepository.findProductsByIsActive(true);
        log.info("Returning list of active products, size: {}", activeProductList.size());
        return activeProductList;
    }

    @Override
    public Product getProductByID(Integer ID) {
        log.info("Request Product with ID: {}", ID);
        Optional<Product> optionalProduct = productRepository.findById(ID);
        if (optionalProduct.isEmpty()) {
            log.error("Product not found with ID: {}", ID);
            throw new EntityNotFoundException("Product not found with ID: " + ID);
        }
        log.info("Returning Product with ID: {}", ID);
        return optionalProduct.get();
    }

}
