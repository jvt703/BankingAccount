package dev.n1t.account.controller;

import dev.n1t.account.dto.IncomingCreditCardApplicationDto;
import dev.n1t.account.dto.IncomingCreditCardDecisionDto;
import dev.n1t.account.dto.OutgoingCreditCardApplicationDto;
import dev.n1t.account.dto.OutgoingCreditCardDecisionDto;
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

    @PostMapping("/user/{userId}/creditCardApplication")
    public OutgoingCreditCardApplicationDto createCreditCardApplication(
        @Validated @RequestBody IncomingCreditCardApplicationDto incomingCreditCardApplicationDto,
        @PathVariable(value = "userId") long userId
    ){
        return creditCardService.createCreditCardApplication(incomingCreditCardApplicationDto, userId);
    }

    @PostMapping("/creditCardApplication/{creditCardApplicationId}")
    public OutgoingCreditCardDecisionDto createCreditCardApplicationDecision(
            @PathVariable(value = "creditCardApplicationId") long creditCardApplicationId,
            @Validated @RequestBody IncomingCreditCardDecisionDto incomingCreditCardDecisionDto
    ){
        return creditCardService.createCreditCardApplicationDecision(creditCardApplicationId, incomingCreditCardDecisionDto);
    }
}
