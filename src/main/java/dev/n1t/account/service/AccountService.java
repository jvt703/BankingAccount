package dev.n1t.account.service;

import dev.n1t.account.dto.AccountRegistrationDto;
import dev.n1t.account.exception.AccountNotFoundException;
import dev.n1t.account.exception.UserNotFoundException;
import dev.n1t.account.repository.AccountRepository;
import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.account.repository.UserRepository;
import dev.n1t.model.Account;
import dev.n1t.model.AccountType;
import dev.n1t.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            AccountTypeRepository accountTypeRepository
    ){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

    public List<Account> getAccountsByUserId(long userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            return accountRepository.findByUser(user.get());
        } else throw new UserNotFoundException(userId);
    }

    public Account createAccount(@NotNull AccountRegistrationDto accountRegistrationDto, Long userId){
        Optional<AccountType> accountType = accountTypeRepository.findById(accountRegistrationDto.getAccountTypeId());
        Optional<User> user = userRepository.findById(userId);

        Account account = new Account();
        if(user.isPresent() && accountType.isPresent()){
            account.setAccountName(accountRegistrationDto.getAccountName());
            account.setAccountType(accountType.get());
            account.setUser(user.get());

            account.setBalance(0.0);
            account.setActive(true);
            account.setConfirmation(false);
            account.setPointsBalance(0L);
            account.setCreatedDate(new Date().getTime());

            accountRepository.save(account);
        }
        return account;
    }

    public Account deleteAccount(long userId, long accountId){
        Optional<User> user = userRepository.findById(userId);
        Optional<Account> account = accountRepository.findById(accountId);

        Account output = new Account();
        if(user.isPresent()){
            if(account.isPresent()){
                if(account.get().getUser().equals(user.get())){
                    /* todo: the above line seems like bad practice
                     * you'd think the first account you create would have an id of 1 since its relative to your account
                     * perhaps a field should be added to be used like an id relative to the account
                     * the actual account id would not be used
                     */
                    output = account.get();

                    accountRepository.delete(output);

                    return output;
                } //todo: if the above suggestion isnt implemented add an exception here
            } else throw new AccountNotFoundException(accountId);
        } else throw new UserNotFoundException(userId);

        return output;
    }


}
