package dev.n1t.account.controller;

import dev.n1t.account.service.AccountTypeService;
import dev.n1t.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
