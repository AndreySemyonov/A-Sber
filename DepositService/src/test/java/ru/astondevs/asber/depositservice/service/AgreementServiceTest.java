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
import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Product;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;
import ru.astondevs.asber.depositservice.entity.enums.SchemaName;
import ru.astondevs.asber.depositservice.mapper.AgreementMapper;
import ru.astondevs.asber.depositservice.mapper.AgreementMapperImpl;
import ru.astondevs.asber.depositservice.repository.AccountRepository;
import ru.astondevs.asber.depositservice.repository.AgreementRepository;
import ru.astondevs.asber.depositservice.repository.ProductRepository;
import ru.astondevs.asber.depositservice.service.impl.AgreementServiceImpl;
import ru.astondevs.asber.depositservice.util.exception.CanNotChangeRenewalStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AgreementServiceTest {

    @InjectMocks
    private AgreementServiceImpl agreementService;
    @Mock
    private AgreementRepository agreementRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private AgreementMapper agreementMapper = new AgreementMapperImpl();
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    private Agreement agreement;
    private Product product;
    private Account account;
    private UUID clientId;
    private AccountNumberDto accountNumberDto;
    private AbsDepositAgreementMessageDto absDepositAgreementMessageDto;

    @BeforeEach
    void setup() {
        clientId = UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");
        product = Product.builder()
                .id(1)
                .name("activeProduct1")
                .schemaName(SchemaName.FIXED)
                .interestRateEarly(new BigDecimal(1))
                .isCapitalization(true)
                .amountMin(new BigDecimal(1000))
                .amountMax(new BigDecimal(1000000))
                .currencyCode(CurrencyCode.EUR)
                .isActive(true)
                .isRevocable(true)
                .minInterestRate(new BigDecimal(1000))
                .maxInterestRate(new BigDecimal(11000))
                .minDurationMonths(1)
                .maxDurationMonths(2)
                .activeUntil(LocalDate.now().plusMonths(2))
                .build();
        agreement = Agreement.builder()
                .id(UUID.fromString("b289e118-5eae-11ed-9b6a-0242ac120002"))
                .agreementNumber("agreementNumber")
                .interestRate(new BigDecimal(2))
                .startDate(LocalDateTime.from(ZonedDateTime.of(2022, 11, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .endDate(LocalDateTime.from(ZonedDateTime.of(2022, 12, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .initialAmount(new BigDecimal(2))
                .currentBalance(new BigDecimal(2))
                .isActive(true)
                .autoRenewal(true)
                .productId(product)
                .build();
        account = Account.builder()
                .accountNumber("accountnumber1")
                .id(UUID.fromString("298060ff-ed9b-4745-857c-a7a2f4a2e3a4"))
                .clientId(clientId)
                .currencyCode(CurrencyCode.USD)
                .currentBalance(new BigDecimal(2))
                .openDate(LocalDate.of(2021, 4, 4))
                .closeDate(LocalDate.of(2031, 4,4))
                .isActive(true)
                .salaryProject("salaryproject")
                .blockedSum(new BigDecimal(1))
                .build();
        accountNumberDto = AccountNumberDto.builder()
                .accountNumber("accountnumber1").build();
        absDepositAgreementMessageDto = AbsDepositAgreementMessageDto.builder()
                .startDate(LocalDateTime.from(ZonedDateTime.of(2022, 11, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .endDate(LocalDateTime.from(ZonedDateTime.of(2022, 12, 25, 4, 4, 4, 4, ZoneId.systemDefault())))
                .agreementNumber("agreementNumber")
                .productId(1)
                .isActive(true)
                .initialAmount(new BigDecimal("2"))
                .cardNumber("123456789")
                .autoRenewal(true)
                .interestRate(new BigDecimal("2"))
                .build();
    }

    @Test
    @DisplayName("If user wants to cancel renewal then renewal status should change")
    void changeRenewalStatus_shouldChangeRenewalStatus() {
        when(agreementRepository.findById(any())).thenReturn(Optional.of(agreement));

        agreementService.changeAutoRenewalStatus(UUID.fromString("b289e118-5eae-11ed-9b6a-0242ac120002"),
                UUID.fromString("38dca868-5eaa-11ed-9b6a-0242ac120005"),
                false);

        assertEquals(false, agreement.getAutoRenewal());
    }

    @Test
    @DisplayName("If user wants to cancel renewal but product ends earlier than agreement does then throw CanNotChangeRenewalStatusException")
    void changeRenewalStatus_shouldThrowCanNotChangeRenewalStatusException() {
        agreement.setAutoRenewal(false);
        product.setActiveUntil(LocalDate.of(2022,12, 24));

        when(agreementRepository.findById(any())).thenReturn(Optional.of(agreement));

        assertThrows(CanNotChangeRenewalStatusException.class, () -> agreementService.changeAutoRenewalStatus(UUID.fromString("b289e118-5eae-11ed-9b6a-0242ac120002"),
                UUID.fromString("38dca868-5eaa-11ed-9b6a-0242ac120005"),
                true));
    }

    @Test
    @DisplayName("If user wants to revoke deposit then deposit status should change")
    void revokeDeposit_shouldChangeDepositStatus(){
        when(agreementRepository.findById(any())).thenReturn(Optional.of(agreement));
        when(accountService.getAccountByAccountNumber(any())).thenReturn(account);

        agreementService.revokeDeposit(UUID.fromString("b289e118-5eae-11ed-9b6a-0242ac120002"), accountNumberDto);

        assertEquals(false, agreement.getIsActive());
    }

    @Test
    @DisplayName("If user wants to get agreement by id")
    void getAgreementById_shouldReturnAgreement() {
        when(agreementRepository.findById(any())).thenReturn(Optional.of(agreement));

        agreementService.getAgreementById(UUID.fromString("a965e7ee-5eae-11ed-9b6a-0242ac120002"));

        assertEquals(true, agreement.getAutoRenewal());
        assertEquals("agreementNumber", agreement.getAgreementNumber());
    }

    @Test
    @DisplayName("If user wants to get agreement by id but product isn't active any more")
    void getAgreementById_shouldChangeAutoRenewalToFalseAndReturnAgreement() {
        product.setIsActive(false);

        when(agreementRepository.findById(any())).thenReturn(Optional.of(agreement));

        agreementService.getAgreementById(UUID.fromString("a965e7ee-5eae-11ed-9b6a-0242ac120002"));

        assertEquals(false, agreement.getAutoRenewal());
        assertEquals("agreementNumber", agreement.getAgreementNumber());
    }

    @Test
    @DisplayName("User wants to create Agreement from valid Dto")
    void createAgreementFromAbsDepositAgreementMessageDto_shouldCreateNewAgreement() {
        when(accountService.getAccountByCardNumber(absDepositAgreementMessageDto.getCardNumber())).thenReturn(account);
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(product));

        Agreement testAgreement = agreementService.createAgreementFromAbs(absDepositAgreementMessageDto);

        assertEquals(testAgreement.getAgreementNumber(), agreement.getAgreementNumber());
        assertEquals(testAgreement.getEndDate(), agreement.getEndDate());
        assertEquals(testAgreement.getInitialAmount(), agreement.getInitialAmount());
    }
}
