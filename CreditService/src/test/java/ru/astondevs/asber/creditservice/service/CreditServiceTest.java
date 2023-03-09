package ru.astondevs.asber.creditservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.enums.CardStatus;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.creditservice.entity.enums.PaymentSystem;
import ru.astondevs.asber.creditservice.repository.AccountRepository;
import ru.astondevs.asber.creditservice.repository.CardRepository;
import ru.astondevs.asber.creditservice.service.impl.CreditServiceImpl;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CreditServiceTest {

    @InjectMocks
    CreditServiceImpl creditService;
    @Mock
    AccountRepository accountRepository;

    @Mock
    CardRepository cardRepository;

    private Account account;
    private UUID clientId;
    private UUID creditId;
    private Card card;

    @BeforeEach
    void setUp() {
        clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        creditId = UUID.fromString("9093905a-6e6b-11ed-a1eb-0242ac120001");
        account = Account.builder()
                .id(UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001"))
                .accountNumber("account_number1")
                .principalDebt(BigDecimal.ZERO)
                .interestDebt(BigDecimal.ZERO)
                .isActive(true)
                .openingDate(LocalDate.of(2020, 04, 15))
                .closingDate(null)
                .currencyCode(CurrencyCode.RUB)
                .outstandingPrincipalDebt(BigDecimal.ZERO)
                .outstandingInterestDebt(BigDecimal.ZERO)
                .build();
        card = Card.builder()
                .id(UUID.fromString("46561a3e-6e80-11ed-a1eb-0242ac120001"))
                .cardNumber("408181057000001")
                .account(account)
                .transactionLimit(new BigDecimal(1000000))
                .status(CardStatus.ACTIVE)
                .expirationDate(LocalDate.ofYearDay(2030, 5))
                .holderName("IVAN IVANOV")
                .paymentSystem(PaymentSystem.MIR)
                .isVirtual(true)
                .coBrand(null)
                .balance(BigDecimal.ZERO)
                .build();
    }

    @DisplayName("If user wants to get his credits and clientId is valid then return ClientCreditResponseDto")
    @Test
    void getClientCredits_shouldReturnClientCreditResponseDto() {
        when(accountRepository.findAccountAndActiveCreditAndAgreementAndProductByClientId(clientId)).thenReturn(List.of(account));
        List<Account> creditsFromDb = creditService.getClientCredits(clientId);

        verify(accountRepository, times(1)).findAccountAndActiveCreditAndAgreementAndProductByClientId(clientId);
        assertThat(creditsFromDb.size()).isEqualTo(1);
        assertThat(creditsFromDb.get(0).getAccountNumber()).isEqualTo("account_number1");
    }

    @DisplayName("If user wants to get his credits and clientId is valid then return ClientCreditResponseDto")
    @Test
    void getCredit_shouldReturnCreditResponseDto() {
        when(cardRepository.getFullCardInfoByCreditId(creditId)).thenReturn(Optional.of(card));
        Card cardFromDb = creditService.getCredit(creditId);

        verify(cardRepository, times(1)).getFullCardInfoByCreditId(creditId);
        assertThat(cardFromDb).isEqualTo(card);
    }
}
