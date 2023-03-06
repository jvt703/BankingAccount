package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingAccountTypeDto;
import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeService(AccountTypeRepository accountTypeRepository){
        this.accountTypeRepository = accountTypeRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initializeAccountTypes() {
        accountTypeRepository.deleteAll();

        AccountType checking = new AccountType();
        checking.setAccountTypeName("Checking");
        checking.setDescription("General purpose account for everyday expenses");

        AccountType savings = new AccountType();
        savings.setAccountTypeName("Savings");
        savings.setDescription("Account intended for saving funds");

        AccountType credit = new AccountType();
        credit.setAccountTypeName("Credit");
        credit.setDescription("Account tied to a credit card with a rewards points system");

        AccountType loan = new AccountType();
        loan.setAccountTypeName("Loan");
        loan.setDescription("Account whose balance reflects the owed amount on a loan");

        accountTypeRepository.saveAll(List.of(checking, savings, credit, loan));
    }

    public List<AccountType> getAllAccountTypes(){
        return this.accountTypeRepository.findAll();
    }

}
