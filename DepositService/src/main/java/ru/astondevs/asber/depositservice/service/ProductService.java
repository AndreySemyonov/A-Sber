package ru.astondevs.asber.depositservice.service;

import ru.astondevs.asber.depositservice.entity.Product;

import java.util.List;

/**
 * Service for {@link Product}.
 */
public interface ProductService {
    /**
     *  Method that gets list of deposit products.
     * @return {@link List} of {@link Product}
     */
    List<Product> getProducts();

    /**
     * Method that gets deposit product by product id.
     * @param id Product id from {@link Product}
     * @return {@link Product}
     */
    Product getProductById(Integer id);
}
