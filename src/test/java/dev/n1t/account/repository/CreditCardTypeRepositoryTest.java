package dev.n1t.account.repository;

import dev.n1t.model.CreditCardType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CreditCardTypeRepositoryTest {

    @Autowired CreditCardTypeRepository creditCardTypeRepository;

    @Test
    void findAllReturnsArrayList() {
        List<CreditCardType> creditCardTypes = creditCardTypeRepository.findAll();
        assertEquals(creditCardTypes.getClass(), ArrayList.class);
    }

    @Test
    void NoDefaultCreditCardTypesShouldExist(){
        List<CreditCardType> creditCardTypes = creditCardTypeRepository.findAll();
        assertEquals(creditCardTypes.size(), 0);
    }
}