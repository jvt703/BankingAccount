package dev.n1t.account.controller;

import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AccountTypeController {

    private final AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeController(AccountTypeRepository accountTypeRepository){
        this.accountTypeRepository = accountTypeRepository;
    }

    @GetMapping(path = "/accountTypes", produces = "application/json")
    public List<AccountType> getAccountTypes(){
        return this.accountTypeRepository.findAll();
    }
}
