package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingAccountTypeDto;
import dev.n1t.account.dto.IncomingCreditCardTypeDto;
import dev.n1t.account.exception.CreditCardTypeNotFoundException;
import dev.n1t.account.repository.CreditCardTypeRepository;
import dev.n1t.model.CreditCardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public CreditCardType updateCreditCardType(long creditCardTypeId, IncomingCreditCardTypeDto incomingCreditCardTypeDto){
        Optional<CreditCardType> creditCardType = creditCardTypeRepository.findById(creditCardTypeId);

        if(creditCardType.isPresent()){
            creditCardType.get().setCreditLimit(incomingCreditCardTypeDto.getCreditLimit());
            creditCardType.get().setInterestRate(incomingCreditCardTypeDto.getInterestRate());
            creditCardType.get().setRewardsName(incomingCreditCardTypeDto.getRewardsName());
            creditCardType.get().setMinimumPayment(incomingCreditCardTypeDto.getMinimumPayment());

            return creditCardTypeRepository.save(creditCardType.get());
        } else throw new CreditCardTypeNotFoundException(creditCardTypeId);
    }
}
