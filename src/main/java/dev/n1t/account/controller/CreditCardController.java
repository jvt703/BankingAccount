package dev.n1t.account.controller;

import dev.n1t.account.dto.IncomingCreditCardDto;
import dev.n1t.account.dto.OutgoingCreditCardDto;
import dev.n1t.account.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService){
        this.creditCardService = creditCardService;
    }

    @PostMapping("/user/{userId}/creditCard")
    public OutgoingCreditCardDto createCreditCard(
        @Validated @RequestBody IncomingCreditCardDto incomingCreditCardDto,
        @PathVariable(value = "userId") long userId
    ){
        return this.creditCardService.createCreditCard(incomingCreditCardDto, userId);
    }

}
