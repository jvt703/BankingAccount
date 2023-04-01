package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingApplicationDecisionDto;
import dev.n1t.account.dto.IncomingLoanApplicationDto;
import dev.n1t.account.dto.OutgoingLoanDecisionDto;
import dev.n1t.account.repository.*;
import dev.n1t.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    private Account dummyAccount;
    private AccountType dummyAccountType;
    private ApplicationDetails dummyApplicationDetails;
    private LoanApplication dummyLoanApplication;
    private LoanDetail dummyLoanDetail;
    private IncomingApplicationDecisionDto dummyDecisionDto;
    private IncomingLoanApplicationDto dummyLoanApplicationDto;

    @BeforeEach
    public void setUp() {
        dummyUser = new User(1L, "FirstName", "LastName", "email@example.com", true, "Password", true, System.currentTimeMillis(), null, null);
        dummyAccountType = new AccountType(1L, "AccountTypeName", "Description");
        dummyAccount = new Account(1L, dummyUser, dummyAccountType, 0.0, true, 0L, "AccountName", System.currentTimeMillis());
        dummyApplicationDetails = new ApplicationDetails(1L, dummyUser, 123456789L, "MotherMaidenName", ResidenceOwnershipStatus.OWN, 1000.0, EmploymentStatus.EMPLOYED, 50000.0, null, null);
        dummyLoanApplication = new LoanApplication(1L, dummyApplicationDetails, dummyAccount, 10000.0);
        dummyLoanDetail = new LoanDetail(1L, dummyAccount, 0.08, 1000.0, 10000.0);
        dummyDecisionDto = new IncomingApplicationDecisionDto();
        dummyLoanApplicationDto = new IncomingLoanApplicationDto();
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

}