package ru.astondevs.asber.creditservice.service.impl;

import static ru.astondevs.asber.creditservice.util.SensitiveInformationMasker.getMaskedString;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.creditservice.dto.CreditCardStatusDto;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;
import ru.astondevs.asber.creditservice.repository.CardRepository;
import ru.astondevs.asber.creditservice.repository.CreditOrderRepository;
import ru.astondevs.asber.creditservice.service.CardService;
import ru.astondevs.asber.creditservice.util.SensitiveInformationMasker;
import ru.astondevs.asber.creditservice.util.exception.CanNotChangeCardStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link CardService}. Works with {@link CardRepository} and {@link Card}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CreditOrderRepository creditOrderRepository;

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link CardRepository#findCardsByClientId(UUID)}.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link Card}
     */
    @Override
    public List<Card> getCardsByClientId(UUID clientId) {
        log.info("Request for cards with client id: {}", clientId);
        List<Card> cardsByClientId = cardRepository.findCardsByClientId(clientId);
        log.info("Returning list of cards with client id: {}, list size: {}", clientId,
            cardsByClientId.size());
        return cardsByClientId;
    }

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link CardRepository#findCardByCardNumber(String)}.
     *
     * @param cardNumber Card number from {@link Card}
     * @return {@link Card}
     * @throws EntityNotFoundException if card not found
     */
    @Override
    public Card getCardByCardNumber(String cardNumber) {
        log.info("Request for card by card number: {}",
            SensitiveInformationMasker.getMaskedString(cardNumber));
        Optional<Card> optionalCard = cardRepository.findCardByCardNumber(cardNumber);
        if (optionalCard.isEmpty()) {
            log.error("Card not found by cardNumber: " + cardNumber);
            throw new EntityNotFoundException("Card not found by cardNumber: " + cardNumber);
        }
        Card card = optionalCard.get();
        log.info("Returning card with card number: {}",
            SensitiveInformationMasker.getMaskedString(card.getCardNumber()));
        return card;
    }

    /**
     * {@inheritDoc} For updates data in db uses methods {@link CardRepository#save(Object)}. Before
     * this getting card uses methods {@link CardRepository#findCardByCardNumber(String)}.
     *
     * @param creditCardStatusDto {@link CreditCardStatusDto} with card status and card number
     * @throws EntityNotFoundException         if card not found
     * @throws CanNotChangeCardStatusException if card status equals new value of card status
     */
    @Transactional
    @Override
    public void changeCardStatus(CreditCardStatusDto creditCardStatusDto) {
        log.info("Request for changing card status with card number: {}",
            getMaskedString(creditCardStatusDto.getCardNumber()));
        Card card = getCardByCardNumber(creditCardStatusDto.getCardNumber());
        if (card.getStatus() == creditCardStatusDto.getCardStatus()) {
            log.error("Cannot change status of card with id {}", card.getId());
            throw new CanNotChangeCardStatusException();
        } else {
            log.info("Changing status of card to {}", creditCardStatusDto.getCardStatus());
            card.setStatus(creditCardStatusDto.getCardStatus());
            cardRepository.save(card);
        }
    }

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link CardRepository#findCardByCardId(UUID)}.
     *
     * @param cardId Card id from {@link Card}
     * @return {@link Card}
     * @throws EntityNotFoundException if card not found
     */
    @Override
    public Card getCreditCardInfoByCardId(UUID cardId) {
        log.info("Request for credit card info with id, {}", cardId);
        Optional<Card> optionalCard = cardRepository.findCardByCardId(cardId);
        if (optionalCard.isEmpty()) {
            log.error("Card with id: {} not found", cardId);
            throw new EntityNotFoundException("Card not found by cardId: " + cardId);
        }
        log.info("Returning credit card info with id: {}", cardId);
        return optionalCard.get();
    }
}
