package ru.astondevs.asber.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.depositservice.entity.Product;

import java.util.List;

/**
 * Repository that stores {@link Product} entities.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    /**
     * Method that finds list of products, having active deposit product.
     * @return {@link List} of {@link Product}
     */
    List<Product> findProductsByIsActiveIsTrue();
}
