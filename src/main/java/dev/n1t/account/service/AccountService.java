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
    private final EmailServiceImpl emailService;
    @Autowired
    public AccountService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            AccountTypeRepository accountTypeRepository,
            EmailServiceImpl emailService
    ){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.emailService = emailService;
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
//changed active to false on creation so that we send email to confirm active before we can change things
        if(user.isPresent()){
            if(accountType.isPresent()){
                Account account = Account.builder()
                                .accountName(accountRegistrationDto.getAccountName())
                                .accountType(accountType.get())
                                .user(user.get())
                                .balance(0.0)
                                .active(false)
                                .pointsBalance(0L)
                                .createdDate(new Date().getTime())
                                .build();
//also need to send email here with the account id link
                Account savedAccount = accountRepository.save(account);
                String link = String.format("http://localhost:8081/account/%s/activation?active=true", savedAccount.getId());
                 emailService.sendConfirmationEmail(user.get().getEmail(),link);
                return new OutgoingAccountDto(savedAccount);
            } else throw new AccountTypeNotFoundException(accountRegistrationDto.getAccountTypeId());
        } else throw new UserNotFoundException(userId);
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
            boolean active
    ){
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            account.get().setActive(active);

            accountRepository.save(account.get());

            return new OutgoingAccountDto(account.get());
        } else throw new AccountNotFoundException(accountId);
    }

}
