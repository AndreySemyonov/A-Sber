package ru.astondevs.asber.depositservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.entity.CardProduct;
import ru.astondevs.asber.depositservice.repository.CardProductRepository;
import ru.astondevs.asber.depositservice.service.CardProductService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Implementation of {@link CardProductService}.
 * Works with services {@link CardProductRepository} and {@link CardProduct}.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardProductServiceImpl implements CardProductService {
    private final CardProductRepository cardProductRepository;

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link CardProductRepository#findCardProductsByIsActiveIsTrue()}.
     * @return {@link List} of {@link CardProduct}
     */
    @Override
    public List<CardProduct> getCardProducts() {
        return cardProductRepository.findCardProductsByIsActiveIsTrue();
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link CardProductRepository#findById(Object)}.
     * @param id Card product id from {@link CardProduct}
     * @return {@link CardProduct}
     * @throws EntityNotFoundException if card product not found
     */
    @Override
    public CardProduct getCardProductById(Integer id) {
        return cardProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CardProduct not found by id = " + id));
    }
}
