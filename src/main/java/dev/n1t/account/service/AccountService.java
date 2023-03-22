package dev.n1t.account.service;

import dev.n1t.account.dto.AccountRegistrationDto;
import dev.n1t.account.dto.IncomingAccountActivationDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public List<OutgoingAccountDto> getAllAccounts(Map<String, String> queryParams) {
        Long id = null;
        String firstName = null;
        String lastName = null;
        Long accountTypeId = null;
        Boolean active = null;
        String accountName = null;
        Long createdDate = null;

        if (queryParams.containsKey("id")) {
            id = Long.parseLong(queryParams.get("id"));
        }

        if (queryParams.containsKey("firstName")) {
            firstName = queryParams.get("firstName");
        }

        if (queryParams.containsKey("lastName")) {
            lastName = queryParams.get("lastName");
        }

        if (queryParams.containsKey("accountTypeId")) {
            accountTypeId = Long.parseLong(queryParams.get("accountTypeId"));
        }

        if (queryParams.containsKey("active")) {
            active = Boolean.parseBoolean(queryParams.get("active"));
        }

        if (queryParams.containsKey("accountName")) {
            accountName = queryParams.get("accountName");
        }

        if (queryParams.containsKey("createdDate")) {
            createdDate = Long.parseLong(queryParams.get("createdDate"));
        }

        List<Account> accounts = accountRepository.findAllByQueryParams(
                id, firstName, lastName, accountTypeId, active, accountName, createdDate
        );

        return accounts.stream()
                .map(OutgoingAccountDto::new)
                .collect(Collectors.toList());
    }

    public OutgoingAccountDto updateAccountActivationStatus(
            long accountId,
            IncomingAccountActivationDto accountActivationDto
    ){
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            account.get().setActive(accountActivationDto.isActive());

            accountRepository.save(account.get());

            return new OutgoingAccountDto(account.get());
        } else throw new AccountNotFoundException(accountId);
    }

}
