package dev.n1t.account.controller;

import dev.n1t.account.service.CreditCardTypeService;
import dev.n1t.model.CreditCardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class CreditCardTypeController {

    private final CreditCardTypeService creditCardTypeService;

    @Autowired
    public CreditCardTypeController(CreditCardTypeService creditCardTypeService){
        this.creditCardTypeService = creditCardTypeService;
    }

    @GetMapping("/creditCardTypes")
    public List<CreditCardType> getCreditCardTypes(){
        return this.creditCardTypeService.getAllCreditCardTypes();
    }

}
