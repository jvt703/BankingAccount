package dev.n1t.account.service;

import dev.n1t.account.dto.*;
import dev.n1t.account.exception.*;
import dev.n1t.account.repository.*;
import dev.n1t.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {
    @InjectMocks
    private CreditCardService creditCardService;

    @Mock
    private CreditCardApplicationRepository creditCardApplicationRepository;

    @Mock
    private ApplicationDetailsRepository applicationDetailsRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private CreditCardTypeRepository creditCardTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreditDetailRepository creditDetailRepository;
    private User dummyUser;
    private CreditCardType dummyCreditCardType;
    private IncomingCreditCardApplicationDto dummyIncomingCreditCardApplicationDto;
    private IncomingApplicationDecisionDto dummyIncomingApplicationDecisionDto;
    private CreditCardApplication dummyCreditCardApplication;
    private ApplicationDetails dummyApplicationDetails;
    private Account dummyAccount;
    private AccountType dummyAccountType;
    private CreditDetail dummyCreditDetail;

    @BeforeEach
    public void setUp() {
        Address dummyAddress = new Address(1L, "Boston", "Massachusetts", "70 Worcester St", "02116");
        dummyUser = new User(1L, "John", "Doe", "john.doe@example.com", true, "Password", true, System.currentTimeMillis(), dummyAddress, null);
        dummyCreditCardType = new CreditCardType(1L, "Test", 0.12, 25.00, 2500.00);
        dummyIncomingCreditCardApplicationDto = new IncomingCreditCardApplicationDto();
        dummyIncomingApplicationDecisionDto = new IncomingApplicationDecisionDto();
        dummyApplicationDetails = new ApplicationDetails(1L, dummyUser, 123456789L, "MotherMaidenName", ResidenceOwnershipStatus.OWN, 1000.0, EmploymentStatus.EMPLOYED, 50000.0, null, null);
        dummyCreditCardApplication = new CreditCardApplication(1L, dummyApplicationDetails, dummyCreditCardType);
        dummyAccount = new Account(1L, dummyUser, dummyAccountType, 0.0, true, 0L, "AccountName", System.currentTimeMillis());
        dummyAccountType = new AccountType(1L, "Credit", "Description");
        dummyCreditDetail = new CreditDetail(1L, dummyAccount, dummyCreditCardType);
    }

    @Test
    public void testSubmitCreditCardApplication() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(creditCardTypeRepository.findById(anyLong())).thenReturn(Optional.of(dummyCreditCardType));
        when(applicationDetailsRepository.save(any(ApplicationDetails.class))).thenReturn(dummyApplicationDetails);
        when(creditCardApplicationRepository.save(any(CreditCardApplication.class))).thenReturn(dummyCreditCardApplication);

        OutgoingApplicationDto result = creditCardService.createCreditCardApplication(dummyIncomingCreditCardApplicationDto, 1L);

        assertNotNull(result);
    }

    @Test
    public void testSubmitCreditCardApplication_UserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> creditCardService.createCreditCardApplication(dummyIncomingCreditCardApplicationDto, 1L));
    }

    @Test
    public void testSubmitCreditCardApplication_CreditCardTypeNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(creditCardTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CreditCardTypeNotFoundException.class, () -> creditCardService.createCreditCardApplication(dummyIncomingCreditCardApplicationDto, 1L));
    }

    @Test
    public void testSubmitCreditCardApplication_UserAlreadyHasCardOfTypeException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(creditCardTypeRepository.findById(anyLong())).thenReturn(Optional.of(dummyCreditCardType));
        when(creditDetailRepository.findByCreditCardTypeAndAccountIn(any(CreditCardType.class), anyList())).thenReturn(Optional.of(dummyCreditDetail));

        assertThrows(UserAlreadyHasCardOfTypeException.class, () -> creditCardService.createCreditCardApplication(dummyIncomingCreditCardApplicationDto, 1L));
    }

    @Test
    public void testSubmitCreditCardApplication_UserHasAlreadyAppliedForCardOfTypeException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(creditCardTypeRepository.findById(anyLong())).thenReturn(Optional.of(dummyCreditCardType));
        when(creditDetailRepository.findByCreditCardTypeAndAccountIn(any(CreditCardType.class), anyList())).thenReturn(Optional.empty());
        when(creditCardApplicationRepository.getByCreditCardTypeAndApplicationDetailsIn(any(CreditCardType.class), anyList())).thenReturn(Optional.of(dummyCreditCardApplication));

        assertThrows(UserHasAlreadyAppliedForCardOfTypeException.class, () -> creditCardService.createCreditCardApplication(dummyIncomingCreditCardApplicationDto, 1L));
    }

    @Test
    public void testCreateCreditCardApplicationDecision() {
        dummyIncomingApplicationDecisionDto.setApproved(true);

        when(creditCardApplicationRepository.findById(anyLong())).thenReturn(Optional.of(dummyCreditCardApplication));
        when(accountRepository.save(any(Account.class))).thenReturn(dummyAccount);
        when(accountTypeRepository.findByAccountTypeName(any())).thenReturn(dummyAccountType);
        when(creditDetailRepository.save(any(CreditDetail.class))).thenReturn(dummyCreditDetail);
        when(applicationDetailsRepository.save(any(ApplicationDetails.class))).thenReturn(dummyApplicationDetails);

        OutgoingCreditCardApplicationDto result = creditCardService.createCreditCardApplicationDecision(dummyCreditCardApplication.getId(), dummyIncomingApplicationDecisionDto);

        assertNotNull(result);
    }

    @Test
    public void testCreateCreditCardApplicationDecision_ApplicationDecisionAlreadyMadeException() {
        dummyIncomingApplicationDecisionDto.setApproved(true);
        dummyApplicationDetails.setDecisionDate(Instant.now());

        when(creditCardApplicationRepository.findById(anyLong())).thenReturn(Optional.of(dummyCreditCardApplication));

        assertThrows(ApplicationDecisionAlreadyMadeException.class, () -> creditCardService.createCreditCardApplicationDecision(1L, dummyIncomingApplicationDecisionDto));
    }

    @Test
    public void testCreateCreditCardApplicationDecision_CreditCardApplicationNotFoundException() {
        dummyIncomingApplicationDecisionDto.setApproved(true);

        when(creditCardApplicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CreditCardApplicationNotFoundException.class, () -> creditCardService.createCreditCardApplicationDecision(1L, dummyIncomingApplicationDecisionDto));
    }

    //test query params
}