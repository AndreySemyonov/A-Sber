package ru.astondevs.asber.depositservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.entity.CardProduct;
import ru.astondevs.asber.depositservice.entity.Product;
import ru.astondevs.asber.depositservice.entity.enums.CardStatus;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.depositservice.entity.enums.PaymentSystem;
import ru.astondevs.asber.depositservice.entity.enums.PremiumStatus;
import ru.astondevs.asber.depositservice.entity.enums.SchemaName;
import ru.astondevs.asber.depositservice.repository.AccountRepository;
import ru.astondevs.asber.depositservice.service.impl.AccountServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;

    private UUID clientId;
    private UUID agreementId;
    private UUID cardId;
    private Account account;
    private Product product;
    private Agreement agreement;
    private CardProduct cardProduct;
    private Card card;

    @BeforeEach
    void setUp() {
        clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        agreementId = UUID.fromString("a965e7ee-5eae-11ed-9b6a-0242ac120002");
        cardId = UUID.fromString("c162b31c-5eb9-11ed-9b6a-0242ac120002");

        product = Product.builder()
                .id(1)
                .name("best deposit")
                .schemaName(SchemaName.RECCURING)
                .isCapitalization(true)
                .amountMin(BigDecimal.valueOf(0.0000))
                .amountMax(BigDecimal.valueOf(100000.0000))
                .isActive(true)
                .isRevocable(true)
                .minInterestRate(BigDecimal.valueOf(5.8000))
                .maxInterestRate(BigDecimal.valueOf(7.700))
                .build();

        agreement = Agreement.builder()
                .id(agreementId)
                .productId(product)
                .accountId(account)
                .agreementNumber("8026/80260/12/00382")
                .startDate(LocalDateTime.of(2022, 1, 15,0, 0, 0))
                .endDate(LocalDateTime.of(2023, 1, 15,0,0,0))
                .interestRate(BigDecimal.valueOf(7.2000))
                .autoRenewal(false)
                .initialAmount(BigDecimal.valueOf(100000.0000))
                .currentBalance(BigDecimal.valueOf(100100.0000))
                .isActive(true)
                .build();

        cardProduct = CardProduct.builder()
                .id(1)
                .cardName("Youth Sbercard")
                .paymentSystem(PaymentSystem.MIR)
                .isVirtual(false)
                .premiumStatus(PremiumStatus.CLASSIC)
                .servicePrice(BigDecimal.valueOf(40.0000))
                .productPrice(BigDecimal.valueOf(150.0000))
                .currencyCode(CurrencyCode.RUB)
                .isActive(true)
                .build();

        card = Card.builder()
                .id(cardId)
                .cardNumber("408181057000012 ")
                .accountId(account)
                .status(CardStatus.ACTIVE)
                .expirationDate(LocalDate.of(2011, 4, 15))
                .holderName("IVAN IVANOV")
                .isDefault(true)
                .cardProductId(cardProduct)
                .build();

        account = Account.builder()
                .id(UUID.fromString("38dca868-5eaa-11ed-9b6a-0242ac120002"))
                .accountNumber("accountNumber1")
                .currencyCode(CurrencyCode.RUB)
                .currentBalance(BigDecimal.valueOf(100.0000))
                .openDate(LocalDate.of(2022, 1, 15))
                .closeDate(LocalDate.of(2022, 1, 16))
                .isActive(true)
                .salaryProject("salaryproject1")
                .blockedSum(BigDecimal.valueOf(50.0000))
                .clientId(clientId)
                .agreements(Set.of(agreement))
                .cards(Set.of(card))
                .build();
    }

    @DisplayName("If client deposits not found for clientId then return EntityNotFoundException")
    @Test
    void getClientDeposits_shouldReturnEntityNotFoundException() {
        when(accountRepository.findActiveAccountsWithAgreementsAndCards(clientId)).thenReturn(Set.of(account));

        Set<Account> result = accountService.getClientDeposits(clientId);

        verify(accountRepository, times(1)).findActiveAccountsWithAgreementsAndCards(clientId);
        assertEquals(Set.of(account), result);
    }

    @Test
    @DisplayName("If client cards was found for clientId then return set of ClientCardsResponseDto")
    void getClientCards_shouldReturnSetOfClientCardsResponseDto() {

        when(accountRepository.findActiveAccountsWithCards(clientId)).thenReturn(Set.of(account));

        Set<Account> result = accountService.getClientCards(clientId);

        verify(accountRepository, times(1)).findActiveAccountsWithCards(clientId);
        assertEquals(Set.of(account), result);
    }

    @DisplayName("If client cards is found for clientId then return ClientDepositResponseDto")
    @Test
    void getClientDeposit_shouldReturnClientDepositResponseDto() {
        when(accountRepository.findActiveAccount(clientId, agreementId, cardId)).thenReturn(Optional.of(account));

        Account result = accountService.getClientDeposit(agreementId, clientId, cardId);

        verify(accountRepository, times(1)).findActiveAccount(clientId, agreementId, cardId);
        assertEquals(account, result);
    }

    @Test
    @DisplayName("If client deposit was not found for clientId,agreementId, cardId then return EntityNotFoundException")
    void getClientDeposit_shouldReturnEntityNotFoundException() {
        Throwable thrown = catchThrowable(() -> {
            accountService.getClientDeposit(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        });
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    @DisplayName("If client wants to see all his accounts then return accounts by clientid")
    void getAccounts_shouldReturnAccounts() {
        when(accountRepository.findAccountsByClientId(clientId)).thenReturn(List.of(account));

        List<Account> result = accountService.getAccounts(clientId);

        verify(accountRepository, times(1)).findAccountsByClientId(clientId);
        assertEquals(List.of(account), result);
    }

    @Test
    @DisplayName("If client wants to see the account with account number then return account")
    void getAccountByAccountNumber_shouldReturnAccount() {
        when(accountRepository.findAccountByAccountNumber(account.getAccountNumber())).thenReturn(Optional.of(account));

        Account result = accountService.getAccountByAccountNumber(account.getAccountNumber());

        verify(accountRepository, times(1)).findAccountByAccountNumber(account.getAccountNumber());
        assertEquals(account, result);
    }

    @Test
    @DisplayName("If card not found by cardNumber then return EntityNotFoundException")
    void getAccountByCardNumber_shouldReturnEntityNotFountException() {
        when(accountRepository.findAccountByCardNumber(card.getCardNumber())).thenReturn(Optional.of(account));

        Account result = accountService.getAccountByCardNumber(card.getCardNumber());

        verify(accountRepository, times(1)).findAccountByCardNumber(card.getCardNumber());
        assertEquals(account, result);
    }

    @Test
    @DisplayName("If client has active accounts then return count of them")
    void getDepositsCount_shouldReturnDepositsCount() {
        when(accountRepository.findActiveAccountsCountByClientId(clientId)).thenReturn(List.of(account).size());

        Integer result = accountService.getDepositsCount(clientId);

        verify(accountRepository, times(1)).findActiveAccountsCountByClientId(clientId);
        assertEquals(List.of(account).size(), result);
    }
}
