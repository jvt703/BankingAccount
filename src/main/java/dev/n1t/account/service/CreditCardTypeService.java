package dev.n1t.account.service;

import dev.n1t.account.repository.CreditCardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardTypeService {

    private final CreditCardTypeRepository creditCardTypeRepository;

    @Autowired
    public CreditCardTypeService(CreditCardTypeRepository creditCardTypeRepository){
        this.creditCardTypeRepository = creditCardTypeRepository;
    }

}
