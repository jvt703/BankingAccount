package dev.n1t.account.service;

import dev.n1t.account.dto.*;
import dev.n1t.account.exception.*;
import dev.n1t.account.repository.*;
import dev.n1t.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LoanService {

    private final AccountRepository accountRepository;
    private final ApplicationDetailsRepository applicationDetailsRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final LoanDetailRepository loanDetailRepository;

    @Autowired
    public LoanService(
            ApplicationDetailsRepository applicationDetailsRepository,
            LoanApplicationRepository loanApplicationRepository,
            AccountRepository accountRepository,
            UserRepository userRepository,
            AccountTypeRepository accountTypeRepository,
            LoanDetailRepository loanDetailRepository
        ){
        this.applicationDetailsRepository = applicationDetailsRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.loanDetailRepository = loanDetailRepository;
    }

    @Transactional
    public OutgoingLoanDecisionDto createLoanApplicationDecision(long applicationId,  IncomingApplicationDecisionDto decisionDto){
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(applicationId);

        if(loanApplication.isPresent()){
            ApplicationDetails loanApplicationDetails = loanApplication.get().getApplicationDetails();

            double newDebitedAccountBalance = loanApplication.get().getDebitedAccount().getBalance();

            if(loanApplicationDetails.getDecisionDate() == null){

                if(decisionDto.isApproved()){
                    Account inputAccount = Account.builder()
                            .createdDate(new Date().getTime())
                            .user(loanApplicationDetails.getUser())
                            .balance(loanApplication.get().getRequestedAmount())
                            .active(true)
                            .pointsBalance(0L)
                            .accountName("Loan issued " + Instant.now()
                                    .atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("MM.dd.yyyy")))
                            .accountType(accountTypeRepository.findByAccountTypeName("Loan"))
                            .build();

                    Account outputAccount = accountRepository.save(inputAccount);

                    LoanDetail inputLoanDetail = LoanDetail.builder()
                            .account(outputAccount)
                            .initialLoanAmount(loanApplication.get().getRequestedAmount())
                            .interestRate(0.08)
                            .minimumPayment(loanApplication.get().getRequestedAmount() * 0.1) //todo: set interest rate dynamically? maybe determine credit internally? could be fun
                            .build();

                    loanDetailRepository.save(inputLoanDetail);

                    newDebitedAccountBalance += inputLoanDetail.getInitialLoanAmount();
                    loanApplication.get().getDebitedAccount().setBalance(newDebitedAccountBalance);

                    accountRepository.save(loanApplication.get().getDebitedAccount());
                }

                loanApplicationDetails.setApproved(decisionDto.isApproved());
                loanApplicationDetails.setDecisionDate(Instant.now());

                applicationDetailsRepository.save(loanApplicationDetails);

                return new OutgoingLoanDecisionDto(
                        new OutgoingLoanApplicationDto(loanApplication.get()),
                        loanApplication.get().getDebitedAccount().getAccountName(),
                        newDebitedAccountBalance
                );
            } else throw new ApplicationDecisionAlreadyMadeException(loanApplicationDetails);
        } else throw new LoanApplicationNotFoundException(applicationId);
    }

    @Transactional
    public OutgoingApplicationDto createLoanApplication(long userId, IncomingLoanApplicationDto incomingLoanApplicationDto){
        Optional<User> user =  userRepository.findById(userId);
        Optional<Account> debitedAccount = accountRepository.findById(incomingLoanApplicationDto.getDebitedAccountId());

        if(user.isPresent()){
            if(debitedAccount.isPresent()){
                if(debitedAccount.get().getUser().equals(user.get())){
                    if(List.of("Checking", "Savings").contains(debitedAccount.get().getAccountType().getAccountTypeName())){
                        ApplicationDetails inputApplicationDetails = ApplicationDetails.builder()
                                .employmentStatus(incomingLoanApplicationDto.getEmploymentStatus())
                                .residenceOwnershipStatus(incomingLoanApplicationDto.getResidenceOwnershipStatus())
                                .grossAnnualIncome(incomingLoanApplicationDto.getGrossAnnualIncome())
                                .housingPayment(incomingLoanApplicationDto.getHousingPayment())
                                .motherMaidenName(incomingLoanApplicationDto.getMotherMaidenName())
                                .socialSecurityNumber(incomingLoanApplicationDto.getSocialSecurityNumber())
                                .user(user.get())
                                .build();

                        ApplicationDetails outputApplicationDetails = applicationDetailsRepository.save(inputApplicationDetails);

                        LoanApplication loanApplication = LoanApplication.builder()
                                .applicationDetails(outputApplicationDetails)
                                .debitedAccount(debitedAccount.get())
                                .requestedAmount(incomingLoanApplicationDto.getLoanAmount())
                                .build();

                        loanApplicationRepository.save(loanApplication);

                        return new OutgoingApplicationDto(String.format("You have successfully applied for a loan of $%f to be debited to account of id %d", incomingLoanApplicationDto.getLoanAmount(), userId));
                    } else throw new InvalidDebitedAccountTypeException();
                } else throw new AccountDoesNotBelongToUserException(debitedAccount.get().getId(), userId);
            } else throw new AccountNotFoundException(incomingLoanApplicationDto.getDebitedAccountId());
        } else throw new UserNotFoundException(userId);
    }

    public List<OutgoingLoanApplicationDto> getAllLoanApplications(Map<String, String> queryParams) {
        Long id = null;
        String firstName = null;
        String lastName = null;
        Boolean approved = null;
        Boolean decisionMade = null;

        if (queryParams.containsKey("id")) {
            id = Long.parseLong(queryParams.get("id"));
        }

        if (queryParams.containsKey("firstName")) {
            firstName = queryParams.get("firstName");
        }

        if (queryParams.containsKey("lastName")) {
            lastName = queryParams.get("lastName");
        }

        if (queryParams.containsKey("approved")) {
            approved = Boolean.parseBoolean(queryParams.get("approved"));
        }

        if (queryParams.containsKey("decisionMade")) {
            decisionMade = Boolean.parseBoolean(queryParams.get("decisionMade"));
        }

        List<LoanApplication> loanApplications = loanApplicationRepository
                .findAllByQueryParams(id, firstName, lastName, approved, decisionMade);

        return loanApplications.stream()
                .map(OutgoingLoanApplicationDto::new)
                .collect(Collectors.toList());
    }
}
