package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingApplicationDecisionDto;
import dev.n1t.account.dto.IncomingLoanApplicationDto;
import dev.n1t.account.dto.OutgoingLoanDecisionDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ApplicationDetailsRepository applicationDetailsRepository;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private LoanDetailRepository loanDetailRepository;

    private User dummyUser;
    private User dummyUser2;
    private Account dummyAccount;
    private Account dummyAccount2;
    private AccountType dummyAccountType;
    private AccountType dummyAccountType2;
    private ApplicationDetails dummyApplicationDetails;
    private LoanApplication dummyLoanApplication;
    private LoanDetail dummyLoanDetail;
    private IncomingApplicationDecisionDto dummyDecisionDto;
    private IncomingLoanApplicationDto dummyLoanApplicationDto;

    @BeforeEach
    public void setUp() {
        dummyUser = new User(1L, "FirstName", "LastName", "email@example.com", true, "Password", true, System.currentTimeMillis(), null, null);
        dummyUser2 = new User(2L, "FirstName2", "LastName2", "email2@example.com", true, "Password", true, System.currentTimeMillis(), null, null);
        dummyAccountType = new AccountType(1L, "Checking", "Description");
        dummyAccountType2 = new AccountType(2L, "Loan", "Description");
        dummyAccount = new Account(1L, dummyUser, dummyAccountType, 0.0, true, 0L, "AccountName", System.currentTimeMillis());
        dummyAccount2 = new Account(2L, dummyUser, dummyAccountType2, 0.0, true, 0L, "AccountName2", System.currentTimeMillis());
        dummyApplicationDetails = new ApplicationDetails(1L, dummyUser, 123456789L, "MotherMaidenName", ResidenceOwnershipStatus.OWN, 1000.0, EmploymentStatus.EMPLOYED, 50000.0, null, null);
        dummyLoanApplication = new LoanApplication(1L, dummyApplicationDetails, dummyAccount, 10000.0);
        dummyLoanDetail = new LoanDetail(1L, dummyAccount, 0.08, 1000.0, 10000.0);
        dummyDecisionDto = new IncomingApplicationDecisionDto();
        dummyLoanApplicationDto = new IncomingLoanApplicationDto(1000.0, dummyAccount.getId(), 123456789, "MotherMaidenName", ResidenceOwnershipStatus.OWN, 1000.0, EmploymentStatus.EMPLOYED, 50000.0);
    }
    @Test
    public void testCreateLoanApplicationDecision() {
        dummyDecisionDto.setApproved(true);

        when(loanApplicationRepository.findById(anyLong())).thenReturn(Optional.of(dummyLoanApplication));
        when(accountRepository.save(any(Account.class))).thenReturn(dummyAccount);
        when(accountTypeRepository.findByAccountTypeName(any())).thenReturn(dummyAccountType);
        when(loanDetailRepository.save(any(LoanDetail.class))).thenReturn(dummyLoanDetail);
        when(applicationDetailsRepository.save(any(ApplicationDetails.class))).thenReturn(dummyApplicationDetails);

        OutgoingLoanDecisionDto result = loanService.createLoanApplicationDecision(1L, dummyDecisionDto);

        assertNotNull(result);
        assertEquals(dummyLoanApplication.getRequestedAmount(), result.getLoanApplication().getLoanAmount());
    }

    @Test
    public void testCreateLoanApplicationDecision_ApplicationDecisionAlreadyMadeException() {
        dummyDecisionDto.setApproved(true);
        dummyApplicationDetails.setDecisionDate(Instant.now());

        when(loanApplicationRepository.findById(anyLong())).thenReturn(Optional.of(dummyLoanApplication));

        assertThrows(ApplicationDecisionAlreadyMadeException.class, () -> loanService.createLoanApplicationDecision(1L, dummyDecisionDto));
    }

    @Test
    public void testCreateLoanApplicationDecision_LoanApplicationNotFoundException() {
        dummyDecisionDto.setApproved(true);

        when(loanApplicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LoanApplicationNotFoundException.class, () -> loanService.createLoanApplicationDecision(1L, dummyDecisionDto));
    }

    @Test
    public void testSubmitLoanApplication() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount));
        when(applicationDetailsRepository.save(any(ApplicationDetails.class))).thenReturn(dummyApplicationDetails);
        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(dummyLoanApplication);

        assertNotNull(loanService.createLoanApplication(dummyUser.getId(), dummyLoanApplicationDto).getMessage());
    }

    @Test
    public void testSubmitLoanApplication_UserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> loanService.createLoanApplication(dummyUser.getId(), dummyLoanApplicationDto));
    }

    @Test
    public void testSubmitLoanApplication_AccountNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> loanService.createLoanApplication(dummyUser.getId(), dummyLoanApplicationDto));
    }

    @Test
    public void testSubmitLoanApplication_AccountDoesNotBelongToUserException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser2));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount));

        assertThrows(AccountDoesNotBelongToUserException.class, () -> loanService.createLoanApplication(dummyUser.getId(), dummyLoanApplicationDto));
    }

    @Test
    public void testSubmitLoanApplication_InvalidDebitedAccountTypeException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount2));

        assertThrows(InvalidDebitedAccountTypeException.class, () -> loanService.createLoanApplication(dummyUser.getId(), dummyLoanApplicationDto));
    }

}