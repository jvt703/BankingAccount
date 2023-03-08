package dev.n1t.account.repository;

import dev.n1t.model.Account;
import dev.n1t.model.AccountType;
import dev.n1t.model.CreditCardType;
import dev.n1t.model.CreditDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreditDetailRepositoryTest {

    @Autowired CreditDetailRepository creditDetailRepository;
    @Autowired AccountRepository accountRepository;
    @Autowired AccountTypeRepository accountTypeRepository;
    @Autowired CreditCardTypeRepository creditCardTypeRepository;

    @Autowired CommonDummyData commonDummyData;

    private static Account dummyAccount;
    private static Account dummyAccount1;
    private static CreditCardType dummyCreditCardType;
    private static CreditDetail dummyCreditDetail;

    @BeforeAll
    void setupDummyData(){
        Account inputAccount = new Account();
        inputAccount.setUser(commonDummyData.getTestUser());
        inputAccount.setAccountType(accountTypeRepository.findByAccountTypeName("Credit"));
        inputAccount.setAccountName("Checking 1");
        inputAccount.setActive(true);
        inputAccount.setCreatedDate(new Date().getTime());
        inputAccount.setBalance(0.0);
        inputAccount.setConfirmation(true);
        inputAccount.setPointsBalance(0L);
        dummyAccount = accountRepository.save(inputAccount);

        Account inputAccount1 = new Account();
        inputAccount1.setUser(commonDummyData.getTestUser());
        inputAccount1.setAccountType(accountTypeRepository.findByAccountTypeName("Checking"));
        inputAccount1.setAccountName("Checking 1");
        inputAccount1.setActive(true);
        inputAccount1.setCreatedDate(new Date().getTime());
        inputAccount1.setBalance(0.0);
        inputAccount1.setConfirmation(true);
        inputAccount1.setPointsBalance(0L);
        dummyAccount1 = accountRepository.save(inputAccount1);

        CreditCardType inputCreditCardType = new CreditCardType();
        inputCreditCardType.setMinimumPayment(25.00);
        inputCreditCardType.setCreditLimit(1000.00);
        inputCreditCardType.setRewardsName("crazy rewards");
        inputCreditCardType.setInterestRate(0.14);
        dummyCreditCardType = creditCardTypeRepository.save(inputCreditCardType);

        CreditDetail inputCreditDetail = new CreditDetail();
        inputCreditDetail.setAccount(dummyAccount);
        inputCreditDetail.setCreditCardType(dummyCreditCardType);
        dummyCreditDetail = creditDetailRepository.save(inputCreditDetail);
    }

    @Test
    void findByCreditCardTypeAndAccountInOptionalIsEmptyWhenCreditCardTypeIsNull() {
        //given
        List<Account> accounts = List.of(dummyAccount);

        //when
        Optional<CreditDetail> creditDetail = creditDetailRepository.findByCreditCardTypeAndAccountIn(null, accounts);

        //then
        assertTrue(creditDetail.isEmpty());
    }

    @Test
    void findByCreditCardTypeAndAccountInOptionalIsEmptyWhenAccountListIsEmpty(){
        //when
        Optional<CreditDetail> creditDetail = creditDetailRepository.findByCreditCardTypeAndAccountIn(dummyCreditCardType, List.of());

        //then
        assertTrue(creditDetail.isEmpty());
    }

    @Test
    void findByCreditCardTypeAndAccountInOptionalIsEmptyWhenAccountListDoesntContainCreditAccount(){
        List<Account> accounts = List.of(dummyAccount1);

        //when
        Optional<CreditDetail> creditDetail = creditDetailRepository.findByCreditCardTypeAndAccountIn(dummyCreditCardType, accounts);

        //then
        assertTrue(creditDetail.isEmpty());
    }

    @Test
    void findByCreditCardTypeAndAccountInOptionalIsPresentWhenAccountListContainsCreditAccountAndCreditTypeMatches(){
        List<Account> accounts = List.of(dummyAccount);

        //when
        Optional<CreditDetail> creditDetail = creditDetailRepository.findByCreditCardTypeAndAccountIn(dummyCreditCardType, accounts);

        //then
        assertTrue(creditDetail.isPresent());
    }
}