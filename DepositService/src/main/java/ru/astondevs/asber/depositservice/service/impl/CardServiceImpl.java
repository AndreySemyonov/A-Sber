package ru.astondevs.asber.depositservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.dto.CardStatusDto;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.entity.enums.CardStatus;
import ru.astondevs.asber.depositservice.mapper.CardMapper;
import ru.astondevs.asber.depositservice.repository.AccountRepository;
import ru.astondevs.asber.depositservice.repository.CardProductRepository;
import ru.astondevs.asber.depositservice.repository.CardRepository;
import ru.astondevs.asber.depositservice.service.AccountService;
import ru.astondevs.asber.depositservice.service.CardProductService;
import ru.astondevs.asber.depositservice.service.CardService;
import ru.astondevs.asber.depositservice.util.exception.CanNotChangeCardStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

/**
 *  Implementation of {@link CardService}.
 *  Works with {@link CardRepository} and {@link Card},
 *  also services {@link AccountService} and {@link CardProductService}
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CardProductRepository cardProductRepository;
    private final CardProductService cardProductService;


    /**
     * {@inheritDoc}
     * For updates data in db uses methods {@link CardRepository#save(Object)}.
     * Before this getting card uses {@link CardRepository#findById(Object)}.
     * @param cardId Card id from {@link Card}
     * @param cardStatusDto {@link CardStatusDto} with new value of card status from
     * @throws EntityNotFoundException if agreement not found
     * @throws CanNotChangeCardStatusException if getting card status equals new value of card status
     */
    @Transactional
    @Override
    public void changeCardStatus(UUID cardId, CardStatusDto cardStatusDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);
        String status = cardStatusDto.getStatus().toString();
        if (card.getStatus().toString().equals(status)) {
            throw new CanNotChangeCardStatusException();
        } else {
            card.setStatus(CardStatus.valueOf(status));
            cardRepository.save(card);
        }
    }

    /**
     * Method that maps {@link NewCardResponseDto} to {@link Card}
     *
     * @param message - {@link NewCardResponseDto}
     * @return {@link Card}
     */
    @Override
    public Card createCardFromAbs(NewCardResponseDto message) {
        Card card = cardMapper.cardFromNewCardResponseDto(message);
        card.setStatus(CardStatus.ACTIVE);
        card.setAccountId(accountRepository.findAccountByAccountNumber(message.getAccountNumber()).orElseThrow());
        card.setCardProductId(cardProductRepository.findById(message.getCardProductId()).orElseThrow());

        cardRepository.save(card);

        return card;
    }
}
