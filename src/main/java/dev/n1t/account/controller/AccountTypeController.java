package dev.n1t.account.controller;

import dev.n1t.account.service.AccountTypeService;
import dev.n1t.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(path = "/accountTypes/{userId}", produces = "application/json")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public List<AccountType> getAccountTypes(
            @PathVariable(value = "userId") long userId,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId){
        return this.accountTypeService.getAllAccountTypes();
    }

}
