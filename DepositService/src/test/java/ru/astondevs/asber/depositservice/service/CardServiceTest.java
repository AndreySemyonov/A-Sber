package ru.astondevs.asber.depositservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.depositservice.dto.CardStatusDto;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.entity.CardProduct;
import ru.astondevs.asber.depositservice.entity.enums.CardStatus;
import ru.astondevs.asber.depositservice.entity.enums.DigitalWallet;
import ru.astondevs.asber.depositservice.mapper.CardMapper;
import ru.astondevs.asber.depositservice.mapper.CardMapperImpl;
import ru.astondevs.asber.depositservice.repository.AccountRepository;
import ru.astondevs.asber.depositservice.repository.CardProductRepository;
import ru.astondevs.asber.depositservice.repository.CardRepository;
import ru.astondevs.asber.depositservice.service.impl.CardServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CardServiceTest {

    @InjectMocks
    private CardServiceImpl cardService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private CardProductService cardProductService;
    @Spy
    private CardMapper cardMapper = new CardMapperImpl();
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CardProductRepository cardProductRepository;
    private Card activeCard;
    private CardStatusDto cardStatusDto;
    private UUID cardId;
    private Account account;
    private CardProduct cardProduct;
    private Card card;
    private NewCardResponseDto newCardResponseDto;

    @BeforeEach
    void setup() {
        cardId = UUID.fromString("c162b31c-5eb9-11ed-9b6a-0242ac120002");
        activeCard = Card.builder()
                .id(UUID.fromString("c162b31c-5eb9-11ed-9b6a-0242ac120002"))
                .cardNumber("408181057000012")
                .accountId(null)
                .transactionLimit(new BigDecimal(6))
                .status(CardStatus.ACTIVE)
                .expirationDate(LocalDate.ofYearDay(2030, 5))
                .holderName("IVAN IVANOV")
                .digitalWallet(DigitalWallet.APPLEPAY)
                .isDefault(true)
                .cardProductId(null)
                .build();
        cardStatusDto = CardStatusDto.builder()
                .cardNumber("408181057000012")
                .status(ru.astondevs.asber.depositservice.dto.CardStatus.BLOCKED)
                .build();
        card = Card.builder()
                .id(null)
                .cardNumber("newCardNumber")
                .accountId(null)
                .transactionLimit(BigDecimal.valueOf(1000000.00))
                .status(CardStatus.ACTIVE)
                .expirationDate(LocalDate.now())
                .holderName("Ivan Ivanov")
                .digitalWallet(DigitalWallet.MIRPAY)
                .cardProductId(null)
                .build();
        account = Account.builder()
                .accountNumber("accountNumber")
                .build();
        cardProduct = CardProduct.builder()
                .id(1)
                .cardName("cardName")
                .build();
        newCardResponseDto = NewCardResponseDto.builder()
                .accountNumber("accountnumber")
                .transactionLimit(BigDecimal.valueOf(100))
                .expirationDate(LocalDate.parse("2011-04-15"))
                .holderName("IVANOV")
                .digitalWallet(ru.astondevs.asber.depositservice.dto.DigitalWallet.APPLEPAY)
                .cardProductId(1)
                .cardNumber("newCardNumber")
                .isDefault(true)
                .build();

    }

    @Test
    @DisplayName("If user wants to change card status then change card status")
    void changeCardStatus_shouldChangeCardStatus() {
        when(cardRepository.findById(any())).thenReturn(Optional.of(activeCard));

        cardService.changeCardStatus(cardId, cardStatusDto);

        verify(cardRepository, times(1)).findById(any());
        assertEquals(CardStatus.BLOCKED, activeCard.getStatus());
    }

    @Test
    @DisplayName("If input DTO is valid then create new card")
    void createCardFromAbs_shouldCreateNewCard() {
        when(accountRepository.findAccountByAccountNumber(newCardResponseDto.getAccountNumber())).thenReturn(Optional.ofNullable(account));
        when(cardProductRepository.findById(newCardResponseDto.getCardProductId())).thenReturn(Optional.ofNullable(cardProduct));

        Card testCard = cardService.createCardFromAbs(newCardResponseDto);

        verify(accountRepository, times(1)).findAccountByAccountNumber(newCardResponseDto.getAccountNumber());
        verify(cardProductRepository, times(1)).findById(newCardResponseDto.getCardProductId());
        assertEquals(card.getCardNumber(), newCardResponseDto.getCardNumber());

    }
}
