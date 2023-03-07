package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingLoanDto;
import dev.n1t.account.dto.OutgoingLoanDto;
import dev.n1t.account.exception.AccountDoesNotBelongToUserException;
import dev.n1t.account.exception.AccountNotFoundException;
import dev.n1t.account.exception.UserNotFoundException;
import dev.n1t.account.repository.AccountRepository;
import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.account.repository.LoanDetailRepository;
import dev.n1t.account.repository.UserRepository;
import dev.n1t.model.Account;
import dev.n1t.model.LoanDetail;
import dev.n1t.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoanService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final LoanDetailRepository loanDetailRepository;

    @Autowired
    public LoanService(
            AccountRepository accountRepository,
            UserRepository userRepository,
            AccountTypeRepository accountTypeRepository,
            LoanDetailRepository loanDetailRepository
        ){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.loanDetailRepository = loanDetailRepository;
    }

    public OutgoingLoanDto createLoan(long userId, IncomingLoanDto incomingLoanDto){
        Optional<User> user =  userRepository.findById(userId);
        Optional<Account> debitedAccount = accountRepository.findById(incomingLoanDto.getDebitedAccountId());

        if(user.isPresent()){
            if(debitedAccount.isPresent()){
                if(debitedAccount.get().getUser().equals(user.get())){
                    Account inputAccount = new Account();
                    inputAccount.setCreatedDate(new Date().getTime());
                    inputAccount.setUser(user.get());
                    inputAccount.setBalance(incomingLoanDto.getLoanAmount());
                    inputAccount.setActive(true);
                    inputAccount.setConfirmation(false);
                    inputAccount.setPointsBalance(0L);
                    inputAccount.setAccountName("Loan");
                    inputAccount.setAccountType(accountTypeRepository.findByAccountTypeName("Loan"));

                    Account outputAccount = accountRepository.save(inputAccount);

                    LoanDetail inputLoanDetail = new LoanDetail();
                    inputLoanDetail.setAccount(outputAccount);
                    inputLoanDetail.setInitialLoanAmount(incomingLoanDto.getLoanAmount());
                    inputLoanDetail.setInterestRate(0.08); //todo: set interest rate dynamically? maybe determine credit internally? could be fun
                    inputLoanDetail.setMinimumPayment(incomingLoanDto.getLoanAmount() * 0.1);

                    LoanDetail outputLoanDetail = loanDetailRepository.save(inputLoanDetail);

                    double newBalance = debitedAccount.get().getBalance() + inputLoanDetail.getInitialLoanAmount();
                    debitedAccount.get().setBalance(newBalance);

                    return new OutgoingLoanDto(outputLoanDetail, outputAccount);
                } else throw new AccountDoesNotBelongToUserException(debitedAccount.get().getId(), userId);
            } else throw new AccountNotFoundException(incomingLoanDto.getDebitedAccountId());
        } else throw new UserNotFoundException(userId);
    }
}
