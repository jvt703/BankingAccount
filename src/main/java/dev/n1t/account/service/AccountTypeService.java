package dev.n1t.account.service;

import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeService(AccountTypeRepository accountTypeRepository){
        this.accountTypeRepository = accountTypeRepository;
    }

    public List<AccountType> getAllAccountTypes(){
        return this.accountTypeRepository.findAll();
    }
}
