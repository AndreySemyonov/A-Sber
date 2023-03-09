package ru.astondevs.asber.depositservice.service;

import ru.astondevs.asber.depositservice.entity.CardProduct;

import java.util.List;

/**
 * Service for {@link CardProduct}.
 */
public interface CardProductService {
    /**
     * Method that gets list of card products.
     * @return {@link List} of {@link CardProduct}
     */
    List<CardProduct> getCardProducts();

    /**
     * Method that gets card products by card product id.
     * @param id Card product id from {@link CardProduct}
     * @return {@link CardProduct}
     */
    CardProduct getCardProductById(Integer id);
}
