package ru.astondevs.asber.creditservice.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.asber.creditservice.ExternalSystemConfig;
import ru.astondevs.asber.creditservice.entity.Account;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("If client wants to get list of accounts by client id then return it")
    void findAccountAndActiveCreditAndAgreementAndProductByClientId_shouldReturnListOfAccounts() {
        UUID clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");

        List<Account> accountList = accountRepository.findAccountAndActiveCreditAndAgreementAndProductByClientId(
            clientId);

        assertNotNull(accountList.get(0));
        assertEquals(UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001"),
            accountList.get(0).getId());
        assertEquals("account_number1", accountList.get(0).getAccountNumber());
    }

    @Test
    @DisplayName("If client wants to get account by credit id then return it")
    void findAccountByCreditId_shouldReturnAccount() {
        UUID creditId = UUID.fromString("9093905a-6e6b-11ed-a1eb-0242ac120001");

        Optional<Account> optionalAccount = accountRepository.findAccountByCreditId(creditId);

        assertDoesNotThrow(optionalAccount::get);
        assertEquals(UUID.fromString("7daa6ee6-6e7a-11ed-a1eb-0242ac120001"),
            optionalAccount.get().getId());
        assertEquals("account_number1", optionalAccount.get().getAccountNumber());

    }

    @Test
    @DisplayName("If client wants to get account count by client id then return it")
    void findActiveAccountsCountByClientId_shouldReturnAccountCount() {
        UUID clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        Integer count = accountRepository.findActiveAccountsCountByClientId(clientId);
        assertEquals(1, count);

    }

    @Test
    @DisplayName("If client wants to get list of accounts by non existing client id then return empty list of accounts")
    void findAccountAndActiveCreditAndAgreementAndProductByClientId_shouldReturnEmptyList() {
        UUID clientId = UUID.randomUUID();
        List<Account> accountList = accountRepository.findAccountAndActiveCreditAndAgreementAndProductByClientId(
            clientId);
        assertEquals(0, accountList.size());
    }

    @Test
    @DisplayName("If client wants to get account by non existing credit id then should throw no such element exception")
    void findAccountByCreditId_shouldThrowNoSuchElementException() {
        UUID creditId = UUID.randomUUID();

        Optional<Account> optionalAccount = accountRepository.findAccountByCreditId(creditId);

        assertThrows(NoSuchElementException.class, optionalAccount::get);

    }

    @Test
    @DisplayName("If client wants to get account count by non existing client id then return zero")
    void findActiveAccountsCountByNonExistingClientId_shouldReturnZero() {
        UUID clientId = UUID.randomUUID();
        Integer count = accountRepository.findActiveAccountsCountByClientId(clientId);
        assertEquals(0, count);

    }
}