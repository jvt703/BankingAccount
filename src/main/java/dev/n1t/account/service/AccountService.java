package dev.n1t.account.service;

import dev.n1t.account.dto.AccountRegistrationDto;
import dev.n1t.account.dto.OutgoingAccountDto;
import dev.n1t.account.exception.AccountDoesNotBelongToUserException;
import dev.n1t.account.exception.AccountNotFoundException;
import dev.n1t.account.exception.AccountTypeNotFoundException;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<OutgoingAccountDto> getAccountsByUserId(long userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            return accountRepository.findByUser(user.get())
                    .stream()
                    .map(OutgoingAccountDto::new)
                    .collect(Collectors.toList());
        } else throw new UserNotFoundException(userId);
    }

    public OutgoingAccountDto getAccount(long userId, long accountId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            Optional<Account> account = accountRepository.findById(accountId);

            if(account.isPresent()){
                if(account.get().getUser().equals(user.get())){
                    return new OutgoingAccountDto(account.get());
                } else throw new AccountDoesNotBelongToUserException(accountId, userId);
            } else throw new AccountNotFoundException(accountId);
        } else throw new UserNotFoundException(userId);
    }

    public OutgoingAccountDto createAccount(@NotNull AccountRegistrationDto accountRegistrationDto, Long userId){
        Optional<AccountType> accountType = accountTypeRepository.findById(accountRegistrationDto.getAccountTypeId());
        Optional<User> user = userRepository.findById(userId);

        Account account = new Account();

        if(user.isPresent()){
            if(accountType.isPresent()){
                account.setAccountName(accountRegistrationDto.getAccountName());
                account.setAccountType(accountType.get());
                account.setUser(user.get());

                account.setBalance(0.0);
                account.setActive(true);
                account.setConfirmation(false);
                account.setPointsBalance(0L);
                account.setCreatedDate(new Date().getTime());

                accountRepository.save(account);
            } else throw new AccountTypeNotFoundException(accountRegistrationDto.getAccountTypeId());
        } else throw new UserNotFoundException(userId);

        return new OutgoingAccountDto(account);
    }

    public OutgoingAccountDto deleteAccount(long userId, long accountId){
        Optional<User> user = userRepository.findById(userId);
        Optional<Account> account = accountRepository.findById(accountId);

        Account output;
        if(user.isPresent()){
            if(account.isPresent()){
                if(account.get().getUser().equals(user.get())){
                    output = account.get();

                    accountRepository.delete(output);
                } else throw new AccountDoesNotBelongToUserException(accountId, userId);
            } else throw new AccountNotFoundException(accountId);
        } else throw new UserNotFoundException(userId);

        return new OutgoingAccountDto(output);
    }


}
