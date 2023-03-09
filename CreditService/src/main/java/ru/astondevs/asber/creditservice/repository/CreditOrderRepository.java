package ru.astondevs.asber.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.entity.Product;

import java.util.List;
import java.util.UUID;

/**
 * Repository that stores {@link CreditOrder} entities.
 */
public interface CreditOrderRepository extends JpaRepository<CreditOrder, UUID> {

    /**
     * Method that finds list of credit orders with required client id.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link CreditOrder} that associates with {@link Product}
     */
    @Query(value = """
        select co from CreditOrder co
        left join fetch co.product
        where co.clientId=:clientId
        """)
    List<CreditOrder> findCreditOrdersByClientId(UUID clientId);
}
