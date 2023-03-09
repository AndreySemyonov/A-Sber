package ru.astondevs.asber.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.creditservice.entity.Product;

import java.util.List;

/**
 * Repository that stores {@link Product} entities.
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Method that finds list of products with value of isActive from param.
     *
     * @param isActive {@link Boolean} value that means active or inactive product
     * @return {@link List} of {@link Product}
     */
    List<Product> findProductsByIsActive(Boolean isActive);
}
