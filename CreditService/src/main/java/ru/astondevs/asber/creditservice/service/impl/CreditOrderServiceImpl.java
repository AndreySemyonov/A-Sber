package ru.astondevs.asber.creditservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;
import ru.astondevs.asber.creditservice.repository.CreditOrderRepository;
import ru.astondevs.asber.creditservice.service.CreditOrderService;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link CreditOrderService}. Works with {@link CreditOrderRepository} and
 * {@link CreditOrder}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreditOrderServiceImpl implements CreditOrderService {

    private final CreditOrderRepository creditOrderRepository;

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link CreditOrderRepository#findCreditOrdersByClientId(UUID)}.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link CreditOrder}
     */
    @Override
    public List<CreditOrder> getCreditOrdersByClientId(UUID clientId) {
        log.info("Request for credit order list with client id: {}", clientId);
        List<CreditOrder> creditOrderList = creditOrderRepository.findCreditOrdersByClientId(
            clientId);
        log.info("Returning credit order list with client id: {}, list size: {}", clientId,
            creditOrderList.size());
        return creditOrderList;
    }

    /**
     * Method that create a new CreditOrder and saves it into Database.
     *
     * @param creditOrder with input parameters from Post request
     * @return NewCreditOrderResponseDto
     */
    @Override
    @Transactional
    public CreditOrder getNewCreditOrder(CreditOrder creditOrder) {
        log.info("Saving Credit order with product with ID: {} to data base", creditOrder.getProduct().getId());
        creditOrder.setStatus(CreditStatus.ACTIVE);
        CreditOrder savedCreditOrder = creditOrderRepository.save(creditOrder);
        log.info("Successfully saved Credit order with product with ID: {} to data base", creditOrder.getProduct().getId());
        return savedCreditOrder;
    }
}
