package ru.astondevs.asber.creditservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.dto.CreditCardStatusDto;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.entity.enums.CardStatus;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;
import ru.astondevs.asber.creditservice.entity.enums.CreditType;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.creditservice.entity.enums.PaymentSystem;
import ru.astondevs.asber.creditservice.repository.CardRepository;
import ru.astondevs.asber.creditservice.service.impl.CardServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CardServiceTest {

    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardRepository cardRepository;

    private Account account;
    private Card card;
    private Card activeCard;
    private UUID existingClient = UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001");
    private CreditCardStatusDto cardStatusDto;
    private UUID existingCard;

    private Credit credit;

    private Agreement agreement;

    @BeforeEach
    void setup() {
        agreement = Agreement.builder()
                .terminationDate(LocalDate.of(2042,12, 1))
                .build();
        credit = Credit.builder()
                .id(UUID.fromString("33361a3e-6e80-11ed-a1eb-0242ac120001"))
                .agreement(agreement)
                .type(CreditType.ANY_PURPOSE_LOAN)
                .creditLimit(BigDecimal.valueOf(0))
                .currencyCode(CurrencyCode.EUR)
                .interestRate(BigDecimal.valueOf(10))
                .personalGuarantees(true)
                .gracePeriodMonths(12)
                .status(CreditStatus.ACTIVE)
                .latePaymentRate(BigDecimal.valueOf(1))
                .build();
        account = Account.builder()
                .id(UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001"))
                .accountNumber("account_number1")
                .credit(credit)
                .currencyCode(CurrencyCode.USD)
                .principalDebt(BigDecimal.valueOf(0))
                .build();
        card = Card.builder()
                .id(UUID.fromString("46561a3e-6e80-11ed-a1eb-0242ac120001"))
                .balance(new BigDecimal(5))
                .cardNumber("408181057000011")
                .holderName("IVAN IVANOV")
                .expirationDate(LocalDate.of(2020,4, 15))
                .paymentSystem(PaymentSystem.MIR)
                .balance(new BigDecimal(0))
                .status(CardStatus.ACTIVE)
                .transactionLimit(new BigDecimal(1000000))
                .account(account)
                .build();
        activeCard = Card.builder()
                .id(UUID.fromString("46561a3e-6e80-11ed-a1eb-0242ac120001"))
                .cardNumber("408181057000001")
                .account(null)
                .transactionLimit(new BigDecimal(1000000))
                .status(CardStatus.ACTIVE)
                .expirationDate(LocalDate.ofYearDay(2030, 5))
                .holderName("IVAN IVANOV")
                .paymentSystem(PaymentSystem.MIR)
                .isVirtual(true)
                .coBrand(null)
                .build();
        cardStatusDto = CreditCardStatusDto.builder()
                .cardNumber("408181057000002")
                .cardStatus(CardStatus.BLOCKED)
                .build();
        existingCard = UUID.fromString("46561a3e-6e80-11ed-a1eb-0242ac120001");
    }

    @DisplayName("If client wants to see all cards then return all his cards")
    @Test
    void getCards_shouldReturnAllCards() {
        when(cardRepository.findCardsByClientId(any())).thenReturn(List.of(card));

        List<Card> result = cardService.getCardsByClientId(existingClient);

        assertEquals(1, result.size());
        assertEquals("408181057000011", result.get(0).getCardNumber());
        assertEquals(CurrencyCode.USD, result.get(0).getAccount().getCurrencyCode());
    }

    @DisplayName("If user wants to change card status then change card status")
    @Test
    void changeCardStatus_shouldChangeCardStatus() {
        when(cardRepository.findCardByCardNumber(any())).thenReturn(Optional.of(activeCard));

        cardService.changeCardStatus(cardStatusDto);

        verify(cardRepository, times(1)).findCardByCardNumber(any());
        assertEquals(CardStatus.BLOCKED, activeCard.getStatus());
    }
    @DisplayName("If client wants to see all cards then return all his cards")
    @Test
    void getCreditCardInfoByCardId_shouldReturnCreditCardInfoResponseDto() {
        when(cardRepository.findCardByCardId(any())).thenReturn(Optional.of(card));

        Card cardFromDb = cardService.getCreditCardInfoByCardId(existingCard);

        verify(cardRepository).findCardByCardId(any());

        assertEquals("account_number1", cardFromDb.getAccount().getAccountNumber());
        assertEquals("IVAN IVANOV", cardFromDb.getHolderName());
        assertEquals(BigDecimal.valueOf(0), cardFromDb.getAccount().getPrincipalDebt());
        assertEquals(LocalDate.of(2042,12, 1), cardFromDb
                .getAccount()
                .getCredit()
                .getAgreement()
                .getTerminationDate());
    }
}
