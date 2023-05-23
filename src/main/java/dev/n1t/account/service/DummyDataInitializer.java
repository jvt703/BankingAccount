package dev.n1t.account.service;

import dev.n1t.model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dev.n1t.account.repository.RoleRepository;
import dev.n1t.account.repository.UserRepository;
import dev.n1t.account.repository.AddressRepository;
import dev.n1t.account.repository.CreditCardTypeRepository;
import dev.n1t.account.repository.AccountRepository;
import dev.n1t.account.repository.AccountTypeRepository;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Component
public class DummyDataInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
//    private final CreditCardTypeRepository creditCardTypeRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final User testUser;

    @Autowired
    public DummyDataInitializer(
            RoleRepository roleRepository,
            UserRepository userRepository,
            AddressRepository addressRepository,
//            CreditCardTypeRepository creditCardTypeRepository,
            AccountRepository accountRepository,
            AccountTypeRepository accountTypeRepository
    ) {
        this.roleRepository = roleRepository;
        roleRepository.deleteAll();
        Role inputUserRole = Role.builder()
                .roleName("Testing")
                .build();
        Role outputUserRole = roleRepository.save(inputUserRole);

        this.addressRepository = addressRepository;
        addressRepository.deleteAll();
        Address inputUserAddress = Address.builder()
                .city("Boston")
                .zipCode("02116")
                .state("Massachusetts")
                .street("70 Worcester St")
                .build();
        Address outputUserAddress = addressRepository.save(inputUserAddress);

        this.userRepository = userRepository;
        userRepository.deleteAll();
        User inputUser = User.builder()
                .active(true)
                .email("ninetenbankmail@gmail.com")
                .address(outputUserAddress)
                .firstname("Mario")
                .lastname("Mario")
                .role(outputUserRole)
                .emailValidated(true)
                .password("T3$71N6!")
                .birthDate(Date.valueOf("1981-07-09").getTime())
                .build();
        testUser = userRepository.save(inputUser);

//        this.creditCardTypeRepository = creditCardTypeRepository;
//        creditCardTypeRepository.deleteAll();
//        CreditCardType test1 = CreditCardType.builder()
//                .creditLimit(2500.00)
//                .minimumPayment(35.00)
//                .rewardsName("Big Spender Rewards")
//                .interestRate(0.08)
//                .build();
//        CreditCardType test2 = CreditCardType.builder()
//                .creditLimit(300.00)
//                .minimumPayment(25.00)
//                .rewardsName("Credit Starter Rewards")
//                .interestRate(0.14)
//                .build();
//        CreditCardType test3 = CreditCardType.builder()
//                .creditLimit(3000.00)
//                .minimumPayment(50.00)
//                .rewardsName("Fast Cash Rewards")
//                .interestRate(0.10)
//                .build();
//        CreditCardType test4 = CreditCardType.builder()
//                .creditLimit(1250.00)
//                .minimumPayment(25.00)
//                .rewardsName("Rainbow Road Rewards")
//                .interestRate(0.15)
//                .build();
//        creditCardTypeRepository.saveAll(List.of(test1, test2, test3, test4));

        accountTypeRepository.deleteAll();
        AccountType checking = new AccountType();
        checking.setAccountTypeName("Checking");
        checking.setDescription("General purpose account for everyday expenses");
        AccountType savings = new AccountType();
        savings.setAccountTypeName("Savings");
        savings.setDescription("Account intended for saving funds");
        AccountType credit = new AccountType();
        credit.setAccountTypeName("Credit");
        credit.setDescription("Account tied to a credit card with a rewards points system");
        AccountType loan = new AccountType();
        loan.setAccountTypeName("Loan");
        loan.setDescription("Account whose balance reflects the owed amount on a loan");
        accountTypeRepository.saveAll(List.of(checking, savings, credit, loan));

        this.accountTypeRepository = accountTypeRepository;
        this.accountRepository = accountRepository;
        accountRepository.deleteAll();
        Account account = Account.builder()
                .accountName("Checking")
                .accountType(accountTypeRepository.findByAccountTypeName("Checking"))
                .user(testUser)
                .active(true)
                .createdDate(new java.util.Date().getTime())
                .pointsBalance(0L)
                .balance(35.26)
                .build();
        accountRepository.save(account);

        Account account1 = Account.builder()
                .accountName("College savings")
                .accountType(accountTypeRepository.findByAccountTypeName("Savings"))
                .user(testUser)
                .active(true)
                .createdDate(new java.util.Date().getTime())
                .pointsBalance(0L)
                .balance(10.00)
                .build();
        accountRepository.save(account1);

        Account account2 = Account.builder()
                .accountName("Loan issued Mar 23 2023")
                .accountType(accountTypeRepository.findByAccountTypeName("Loan"))
                .user(testUser)
                .active(true)
                .createdDate(new java.util.Date().getTime())
                .pointsBalance(0L)
                .balance(104.23)
                .build();
        accountRepository.save(account2);

        Account account3 = Account.builder()
                .accountName("Imaginary Rewards Card")
                .accountType(accountTypeRepository.findByAccountTypeName("Credit"))
                .user(testUser)
                .active(true)
                .createdDate(new java.util.Date().getTime())
                .pointsBalance(0L)
                .balance(0.00)
                .build();
        accountRepository.save(account3);
    }
}
