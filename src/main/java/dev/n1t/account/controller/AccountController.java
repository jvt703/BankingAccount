package dev.n1t.account.controller;
import dev.n1t.account.dto.AccountRegistrationDto;
import dev.n1t.account.dto.IncomingAccountActivationDto;
import dev.n1t.account.dto.OutgoingAccountDto;
import dev.n1t.account.service.AccountService;
import dev.n1t.account.service.DummyDataInitializer;
import dev.n1t.model.Account;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
public class AccountController {

    private final AccountService accountService;
    private final DummyDataInitializer dummyDataInitializer;
    @Autowired
    public AccountController(AccountService accountService, DummyDataInitializer dummyDataInitializer){
        this.accountService = accountService;
        this.dummyDataInitializer = dummyDataInitializer;
    }

    //get all accounts visible to a user
    @GetMapping(path = "/user/{userId}/accounts", produces = "application/json")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public List<OutgoingAccountDto> getUserAccounts(
            @PathVariable(value = "userId") long userId,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId
    ){
        return accountService.getAccountsByUserId(userId);
    }

    @GetMapping(path = "/accounts/all", produces = "application/json")
    @PreAuthorize("((#Role.equals('admin'))")
    public List<OutgoingAccountDto> getAllAccounts(
        @RequestParam Map<String, String> queryParams,
        @RequestHeader("Role") String Role
    ){
        return accountService.getAllAccounts(queryParams);
    }

    @GetMapping(path = "/user/{userId}/account/{accountId}", produces = "application/json")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public OutgoingAccountDto getUserAccount(
            @PathVariable(value = "userId") long userId,
            @PathVariable(value = "accountId") long accountId,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId
    ){
        return accountService.getAccount(userId, accountId);
    }

    //create new account (e.g. checking or savings)

    @PostMapping("/user/{userId}/account")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public ResponseEntity<OutgoingAccountDto> createAccount(
            @PathVariable(value = "userId") long userId,
            @Validated @RequestBody AccountRegistrationDto accountRegistrationDto,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId
    ){
        return new ResponseEntity<>(accountService.createAccount(accountRegistrationDto, userId), HttpStatus.CREATED);
    }

    //delete specific account
    @DeleteMapping("/user/{userId}/account/{accountId}")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public OutgoingAccountDto deleteAccount(
            @PathVariable(value = "userId") long userId,
            @PathVariable(value = "accountId") long accountId,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId
    ) {
        return accountService.deleteAccount(userId, accountId);
    }

//    @PutMapping(path = "/account/{accountId}/activation", consumes = "application/json")
//    public OutgoingAccountDto updateAccountActivationStatus(
//            @PathVariable(value = "accountId") long accountId,
//            @Valid @RequestBody IncomingAccountActivationDto accountActivationDto
//    ){
//        return accountService.updateAccountActivationStatus(accountId, accountActivationDto);
//    }
    @GetMapping(path = "/account/{accountId}/activation", produces = "application/json")
    public OutgoingAccountDto updateAccountActivationStatus(
            @PathVariable(value = "accountId") long accountId,
            @RequestParam(value = "active") boolean active,
            @RequestHeader("id") Long requestId
    ){
        // Logic to update the account activation status based on the 'active' query parameter

        // Return the updated account DTO
        return accountService.updateAccountActivationStatus(accountId, active, requestId);
    }

}
