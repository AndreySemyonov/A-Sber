package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.entity.Product;

import java.util.List;

/**
 * Service that works with {@link Product}.
 */
public interface ProductService {

    /**
     * Method that gets list of active products.
     *
     * @return {@link List} of {@link Product}
     */
    List<Product> getActiveProducts();

    /**
     * Method that gets product by  its id.
     * @param ID product id
     * @return {@link Product}
     */
    Product getProductByID(Integer ID);
}
