package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingAccountTypeDto;
import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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

        List<AccountType> existingAccountTypes = accountTypeRepository.findAll();
        if(existingAccountTypes.isEmpty()){
            List<AccountType> stagedAccountTypes = new ArrayList<>();
            AccountType checking = AccountType.builder()
                    .id(1L)
                    .accountTypeName("Checking")
                    .description("General purpose account for everyday expenses")
                    .build();
            stagedAccountTypes.add(checking);

            AccountType savings = AccountType.builder()
                    .id(2L)
                    .accountTypeName("Savings")
                    .description("Account intended for saving funds")
                    .build();
            stagedAccountTypes.add(savings);

            AccountType credit = AccountType.builder()
                    .id(3L)
                    .accountTypeName("Credit")
                    .description("Account tied to a credit card with a rewards points system")
                    .build();
            stagedAccountTypes.add(credit);

            AccountType loan = AccountType.builder()
                    .id(4L)
                    .accountTypeName("Loan")
                    .description("Account whose balance reflects the owed amount on a loan")
                    .build();
            stagedAccountTypes.add(loan);

            accountTypeRepository.saveAll(List.of(checking, savings, credit, loan));
        }
    }

    public List<AccountType> getAllAccountTypes(){
        return this.accountTypeRepository.findAll();
    }

}
