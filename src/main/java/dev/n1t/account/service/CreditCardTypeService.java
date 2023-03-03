package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingAccountTypeDto;
import dev.n1t.account.dto.IncomingCreditCardTypeDto;
import dev.n1t.account.repository.CreditCardTypeRepository;
import dev.n1t.model.CreditCardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardTypeService {

    private final CreditCardTypeRepository creditCardTypeRepository;

    @Autowired
    public CreditCardTypeService(CreditCardTypeRepository creditCardTypeRepository){
        this.creditCardTypeRepository = creditCardTypeRepository;
    }

    public List<CreditCardType> getAllCreditCardTypes(){
        return this.creditCardTypeRepository.findAll();
    }

    public CreditCardType createCreateCreditCardType(IncomingCreditCardTypeDto incomingCreditCardTypeDto){
        CreditCardType temp = new CreditCardType();
        temp.setCreditLimit(incomingCreditCardTypeDto.getCreditLimit());
        temp.setInterestRate(incomingCreditCardTypeDto.getInterestRate());
        temp.setRewardsName(incomingCreditCardTypeDto.getRewardsName());
        temp.setMinimumPayment(incomingCreditCardTypeDto.getMinimumPayment());

        return this.creditCardTypeRepository.save(temp);
    }
}
