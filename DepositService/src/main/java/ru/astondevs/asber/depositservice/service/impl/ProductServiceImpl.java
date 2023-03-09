package ru.astondevs.asber.depositservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.entity.Product;
import ru.astondevs.asber.depositservice.repository.ProductRepository;
import ru.astondevs.asber.depositservice.service.ProductService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Implementation of {@link ProductService}.
 *  Works with {@link ProductRepository} and {@link Product}.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link ProductRepository#findProductsByIsActiveIsTrue()}.
     * @return {@link List} of {@link Product}
     */
    @Override
    public List<Product> getProducts() {
        return productRepository.findProductsByIsActiveIsTrue();
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link ProductRepository#findById(Object)}
     * @param id Product id from {@link Product}
     * @return {@link Product}
     * @throws EntityNotFoundException if product not found
     */
    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by id = " + id));
    }
}
