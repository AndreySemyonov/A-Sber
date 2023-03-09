package ru.astondevs.asber.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.depositservice.entity.CardProduct;

import java.util.List;

/**
 * Repository that stores {@link CardProduct} entities.
 */
@Repository
public interface CardProductRepository extends JpaRepository<CardProduct, Integer> {
    /**
     * Method that finds list of card products, having active card product.
     * @return {@link List} of {@link CardProduct}
     */
    List<CardProduct> findCardProductsByIsActiveIsTrue();
}
