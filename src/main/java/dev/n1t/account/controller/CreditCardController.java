package dev.n1t.account.controller;

import dev.n1t.account.dto.IncomingCreditCardApplicationDto;
import dev.n1t.account.dto.OutgoingCreditCardApplicationDto;
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

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/user/{userId}/creditCard")
//    public OutgoingCreditCardDto createCreditCard(
//        @Validated @RequestBody IncomingCreditCardDto incomingCreditCardDto,
//        @PathVariable(value = "userId") long userId
//    ){
//        return this.creditCardService.createCreditCard(incomingCreditCardDto, userId);
//    }

    @PostMapping("/user/{userId}/creditCardApplication")
    public OutgoingCreditCardApplicationDto createCreditCardApplication(
        @Validated @RequestBody IncomingCreditCardApplicationDto incomingCreditCardApplicationDto,
        @PathVariable(value = "userId") long userId
    ){
        return this.creditCardService.createCreditCardApplication(incomingCreditCardApplicationDto, userId);
    }

}
