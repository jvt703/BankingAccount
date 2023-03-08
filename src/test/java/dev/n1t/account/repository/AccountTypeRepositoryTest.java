package dev.n1t.account.repository;

import dev.n1t.model.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountTypeRepositoryTest {

    @Autowired AccountTypeRepository accountTypeRepository;

    @Test
    void findAllShouldReturnAsAnArrayList() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        assertEquals(accountTypes.getClass(), ArrayList.class);
    }

    @Test
    void findAllShouldReturnAnArrayOfLength4(){
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        assertEquals(accountTypes.size(), 4);
    }

    @Test
    void accountTypeNamedCheckingExists() {
        AccountType checkingAccountType = accountTypeRepository.findByAccountTypeName("Checking");
        assertNotNull(checkingAccountType);
    }

    @Test
    void accountTypeNamedSavingsExists() {
        AccountType checkingAccountType = accountTypeRepository.findByAccountTypeName("Savings");
        assertNotNull(checkingAccountType);
    }

    @Test
    void accountTypeNamedCreditExists() {
        AccountType checkingAccountType = accountTypeRepository.findByAccountTypeName("Credit");
        assertNotNull(checkingAccountType);
    }

    @Test
    void accountTypeNamedLoanExists() {
        AccountType checkingAccountType = accountTypeRepository.findByAccountTypeName("Loan");
        assertNotNull(checkingAccountType);
    }

    @Test
    void findByAccountTypeNameReturnsNullForRandomName(){
        AccountType checkingAccountType = accountTypeRepository.findByAccountTypeName("aspciscjapsiozxuih");
        assertNull(checkingAccountType);
    }

}