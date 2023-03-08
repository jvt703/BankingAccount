package dev.n1t.account.repository;

import dev.n1t.model.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountRepositoryTest {


    @Autowired private AccountRepository accountRepository;
    @Autowired private CommonDummyData commonDummyData;
    @Autowired private AccountTypeRepository accountTypeRepository;


    @Test
    void findByUserReturnsAccountCreatedForUser() {
        //before
        accountRepository.deleteAll();

        Account inputAccount = new Account();
        inputAccount.setUser(commonDummyData.getTestUser());
        inputAccount.setAccountType(accountTypeRepository.findByAccountTypeName("Checking"));
        inputAccount.setBalance(0.0);
        inputAccount.setActive(true);
        inputAccount.setAccountName("Test Account 1");
        inputAccount.setConfirmation(true);
        inputAccount.setCreatedDate(new Date().getTime());
        inputAccount.setPointsBalance(0L);

        System.out.println(inputAccount);
        Account outputAccount = accountRepository.save(inputAccount);

        //when
        List<Account> userAccounts = accountRepository.findByUser(commonDummyData.getTestUser());

        //then
        assertEquals(userAccounts.get(0).getId(), outputAccount.getId());
    }
}