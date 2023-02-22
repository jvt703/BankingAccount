package dev.n1t.account.controller;
import dev.n1t.account.repository.AccountRepository;
import dev.n1t.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping(path = "/user/{userId}/accounts", produces = "application/json")
    public List<Account> getUserAccounts(@PathVariable(value = "userId") User user){
        return accountRepository.findByUser(userId)
    }
}
