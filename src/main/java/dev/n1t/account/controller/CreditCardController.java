package dev.n1t.account.controller;

import dev.n1t.account.dto.*;
import dev.n1t.account.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService){
        this.creditCardService = creditCardService;
    }

    @PostMapping("/user/{userId}/creditCardApplication")
    public OutgoingApplicationDto createCreditCardApplication(
        @Validated @RequestBody IncomingCreditCardApplicationDto incomingCreditCardApplicationDto,
        @PathVariable(value = "userId") long userId
    ){
        return creditCardService.createCreditCardApplication(incomingCreditCardApplicationDto, userId);
    }

    @PostMapping("/creditCardApplication/{creditCardApplicationId}")
    public OutgoingCreditCardDecisionDto createCreditCardApplicationDecision(
            @PathVariable(value = "creditCardApplicationId") long creditCardApplicationId,
            @Validated @RequestBody IncomingApplicationDecisionDto incomingApplicationDecisionDto
    ){
        return creditCardService.createCreditCardApplicationDecision(creditCardApplicationId, incomingApplicationDecisionDto);
    }

    @GetMapping("/creditCardApplications")
    public List<OutgoingCreditCardApplicationDto> getAllCreditCardApplications(){
        return creditCardService.getAllCreditCardApplications();
    }
}
