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
import dev.n1t.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    private User dummyUser;
    private Account dummyAccount;
    private AccountType dummyAccountType;
    private Address dummyAddress;
    private Role dummyRole;
    private List<Account> dummyAccountsList;
    private User dummyUser2;

    @BeforeEach
    public void setUp() {
        dummyAddress = new Address(1L, "City", "State", "Street", "12345");
        dummyRole = new Role(1L, "RoleName");
        dummyUser = new User(1L, "FirstName", "LastName", "email@example.com", true, "Password", true, System.currentTimeMillis(), dummyAddress, dummyRole);
        dummyAccountType = new AccountType(1L, "AccountTypeName", "Description");
        dummyAccount = new Account(1L, dummyUser, dummyAccountType, 0.0, true, 0L, "AccountName", System.currentTimeMillis());
        dummyAccountsList = List.of(dummyAccount);
        dummyUser2 = new User(2L, "AnotherFirstName", "AnotherLastName", "another@example.com", true, "AnotherPassword", true, System.currentTimeMillis(), dummyAddress, dummyRole);
    }

    //getAccount
    @Test
    public void testGetAccount() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount));

        OutgoingAccountDto resultAccount = accountService.getAccount(dummyUser.getId(), dummyAccount.getId());
        assertEquals(dummyAccount.getId(), resultAccount.getId());
    }

    @Test
    public void testGetAccount_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> accountService.getAccount(dummyUser.getId(), dummyAccount.getId()));
    }

    @Test
    public void testGetAccount_AccountNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(dummyUser.getId(), dummyAccount.getId()));
    }

    @Test
    public void testGetAccount_AccountDoesNotBelongToUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser2));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount));
        assertThrows(AccountDoesNotBelongToUserException.class, () -> accountService.getAccount(dummyUser2.getId(), dummyAccount.getId()));
    }

    //getAccountsByUserId
    @Test
    public void testGetAccountsByUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findByUser(any(User.class))).thenReturn(dummyAccountsList);

        List<OutgoingAccountDto> accounts = accountService.getAccountsByUserId(dummyUser.getId());
        assertEquals(dummyAccountsList.size(), accounts.size());
    }

    @Test
    public void testGetAccountsByUserId_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> accountService.getAccountsByUserId(dummyUser.getId()));
    }


    //createAccount
    @Test
    public void testCreateAccount() {
        AccountRegistrationDto accountRegistrationDto = new AccountRegistrationDto();
        accountRegistrationDto.setAccountTypeId(dummyAccountType.getId());
        accountRegistrationDto.setAccountName("NewAccountName");


        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountTypeRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccountType));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0, Account.class));

        OutgoingAccountDto resultAccount = accountService.createAccount(accountRegistrationDto, dummyUser.getId());
        assertEquals("NewAccountName", resultAccount.getAccountName());
    }

    @Test
    public void testCreateAccount_UserNotFound() {
        AccountRegistrationDto accountRegistrationDto = new AccountRegistrationDto();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> accountService.createAccount(accountRegistrationDto, dummyUser.getId()));
    }

    @Test
    public void testCreateAccount_AccountTypeNotFound() {
        AccountRegistrationDto accountRegistrationDto = new AccountRegistrationDto();
        accountRegistrationDto.setAccountTypeId(0L);
        accountRegistrationDto.setAccountName("");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountTypeNotFoundException.class, () -> accountService.createAccount(accountRegistrationDto, dummyUser.getId()));
    }

    //deleteAccount
    @Test
    public void testDeleteAccount(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount));

        assertEquals(dummyAccount.getId(), accountService.deleteAccount(dummyUser.getId(), dummyAccount.getId()).getId());
    }

    @Test
    public void testDeleteAccount_UserNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount));

        assertThrows(UserNotFoundException.class, () -> accountService.deleteAccount(dummyUser.getId(), dummyAccount.getId()));
    }

    @Test
    public void testDeleteAccount_AccountNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccount(dummyUser.getId(), dummyAccount.getId()));
    }

    @Test
    public void testDeleteAccount_AccountDoesNotBelongToUserException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(dummyUser2));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(dummyAccount));

        assertThrows(AccountDoesNotBelongToUserException.class, () -> accountService.deleteAccount(dummyUser.getId(), dummyAccount.getId()));
    }

    @Test
    public void testGetAllAccounts_NoFilters() {
        when(accountRepository.findAllByQueryParams(
                null, null, null, null, null, null, null
        )).thenReturn(dummyAccountsList);

        List<OutgoingAccountDto> accounts = accountService.getAllAccounts(new HashMap<>());
        assertEquals(dummyAccountsList.size(), accounts.size());
    }

    @Test
    public void testGetAllAccounts_IdFilter() {
        Long accountId = dummyAccountsList.get(0).getId();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", accountId.toString());

        when(accountRepository.findAllByQueryParams(
                accountId, null, null, null, null, null, null
        )).thenReturn(Collections.singletonList(dummyAccountsList.get(0)));

        List<OutgoingAccountDto> accounts = accountService.getAllAccounts(queryParams);
        assertEquals(1, accounts.size());
        assertEquals(accountId, accounts.get(0).getId());
    }

    //todo: test other filters partially, fully

}