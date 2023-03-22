package dev.n1t.account.controller;
import dev.n1t.account.dto.AccountRegistrationDto;
import dev.n1t.account.dto.IncomingAccountActivationDto;
import dev.n1t.account.dto.OutgoingAccountDto;
import dev.n1t.account.service.AccountService;
import dev.n1t.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    //get all accounts visible to a user
    //todo: limit visibility to authorized user
    @GetMapping(path = "/user/{userId}/accounts", produces = "application/json")
    public List<OutgoingAccountDto> getUserAccounts(
            @PathVariable(value = "userId") long userId
    ){
        return accountService.getAccountsByUserId(userId);
    }

    @GetMapping(path = "/accounts/all", produces = "application/json")
    public List<OutgoingAccountDto> getAllAccounts(
        @RequestParam Map<String, String> queryParams
    ){
        return accountService.getAllAccounts(queryParams);
    }

    @GetMapping(path = "/user/{userId}/account/{accountId}", produces = "application/json")
    public OutgoingAccountDto getUserAccount(
            @PathVariable(value = "userId") long userId,
            @PathVariable(value = "accountId") long accountId
    ){
        return accountService.getAccount(userId, accountId);
    }

    //create new account (e.g. checking or savings)
    @PostMapping("/user/{userId}/account")
    public OutgoingAccountDto createAccount(
            @PathVariable(value = "userId") long userId,
            @Validated @RequestBody AccountRegistrationDto accountRegistrationDto
    ){
        return accountService.createAccount(accountRegistrationDto, userId);
    }

    //delete specific account
    @DeleteMapping("/user/{userId}/account/{accountId}")
    public OutgoingAccountDto deleteAccount(
            @PathVariable(value = "userId") long userId,
            @PathVariable(value = "accountId") long accountId
    ) {
        return accountService.deleteAccount(userId, accountId);
    }

    @PutMapping("/account/{accountId}/activation")
    public OutgoingAccountDto updateAccountActivationStatus(
            @PathVariable(value = "accountId") long accountId,
            @Validated @RequestBody IncomingAccountActivationDto accountActivationDto
    ){
        return accountService.updateAccountActivationStatus(accountId, accountActivationDto);
    }

}
