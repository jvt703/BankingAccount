package dev.n1t.account.controller;

import dev.n1t.account.service.CreditCardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class CreditCardTypeController {

    private final CreditCardTypeService creditCardTypeService;

    @Autowired
    public CreditCardTypeController(CreditCardTypeService creditCardTypeService){
        this.creditCardTypeService = creditCardTypeService;
    }


}
