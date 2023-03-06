package dev.n1t.account.controller;

import dev.n1t.account.dto.IncomingAccountTypeDto;
import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.account.service.AccountTypeService;
import dev.n1t.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @Autowired
    public AccountTypeController(AccountTypeService accountTypeService){
        this.accountTypeService = accountTypeService;
    }

    @GetMapping(path = "/accountTypes", produces = "application/json")
    public List<AccountType> getAccountTypes(){
        return this.accountTypeService.getAllAccountTypes();
    }

}
