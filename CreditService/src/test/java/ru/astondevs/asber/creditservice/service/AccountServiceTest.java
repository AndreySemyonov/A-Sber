package ru.astondevs.asber.creditservice.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.creditservice.repository.AccountRepository;
import ru.astondevs.asber.creditservice.service.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    private Account account;
    private UUID clientId;
    private Account activeAccount;

    private final UUID existingCreditUUID = UUID.fromString("9093905a-6e6b-11ed-a1eb-0242ac120001");

    private final UUID nonExistingCreditUUID = UUID.fromString(
        "8093905a-6e6b-11ed-a1eb-0242ac120001");

    @BeforeEach
    void setup() {
        clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        account = Account.builder()
                .id(UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001"))
                .accountNumber("account_number1")
                .currencyCode(CurrencyCode.USD)
                .build();
        activeAccount = Account.builder()
                .id(UUID.fromString("38dca868-5eaa-11ed-9b6a-0242ac120002"))
                .credit(null)
                .paymentSchedule(null)
                .accountNumber("accountNumber1")
                .principalDebt(BigDecimal.ONE)
                .interestDebt(BigDecimal.ONE)
                .isActive(true)
                .openingDate(LocalDate.of(2022, 1, 15))
                .closingDate(LocalDate.of(2032, 1, 16))
                .currencyCode(CurrencyCode.RUB)
                .outstandingInterestDebt(BigDecimal.ZERO)
                .outstandingPrincipalDebt(BigDecimal.ZERO)
                .build();
    }

    @DisplayName("If client wants to see account with credit id then return it")
    @Test
    void getAccountByCreditId_shouldReturnAccount() {
        when(accountRepository.findAccountByCreditId(any())).thenReturn(Optional.of(account));

        Account resultAccount = accountService.getAccountByCreditId(existingCreditUUID);

        assertEquals("account_number1", resultAccount.getAccountNumber());
        assertEquals(CurrencyCode.USD, resultAccount.getCurrencyCode());
    }

    @DisplayName("If client wants to see account with non existing credit id then expect EntityNotFoundException")
    @Test
    void getAccountByCreditId_shouldReturnEntityNotFoundError() {
        when(accountRepository.findAccountByCreditId(any())).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountByCreditId(
            nonExistingCreditUUID));
    }

    @DisplayName("If client has active accounts then return count of them")
    @Test
    void getCreditsCount_shouldReturnDepositsCount() {
        when(accountRepository.findActiveAccountsCountByClientId(clientId)).thenReturn(List.of(activeAccount).size());

        Integer result = accountService.getCreditsCount(clientId);

        verify(accountRepository, times(1)).findActiveAccountsCountByClientId(clientId);
        assertEquals(List.of(activeAccount).size(), result);
    }

}
