package ru.astondevs.asber.creditservice.service;

import ru.astondevs.asber.creditservice.entity.CreditOrder;

import java.util.List;
import java.util.UUID;

/**
 * Service that works with {@link CreditOrder}.
 */
public interface CreditOrderService {

    /**
     * Method that gets list of cards by client id.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link CreditOrder}
     */
    List<CreditOrder> getCreditOrdersByClientId(UUID clientId);

    /**
     * Method that saves Credit order from request to database.
     *
     * @param creditOrder Credit order from request
     * @return {@link CreditOrder}
     */
    CreditOrder getNewCreditOrder(CreditOrder creditOrder);
}
