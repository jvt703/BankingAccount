package dev.n1t.account.controller;

import dev.n1t.account.dto.*;
import dev.n1t.account.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class LoanController {

    private final LoanService loanService;

    @Autowired()
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping(path = "/user/{userId}/loan", produces = "application/json")
//    public OutgoingLoanDto createLoan(
//            @PathVariable(value = "userId") long userId,
//            @Validated @RequestBody IncomingLoanDto incomingLoanDto
//        ){
//        return loanService.createLoan(userId, incomingLoanDto);
//    }

    @PostMapping(path = "/user/{userId}/loanApplication", produces = "application/json")
    public OutgoingApplicationDto createLoanApplication(
            @PathVariable(value = "userId") long userId,
            @Validated @RequestBody IncomingLoanApplicationDto incomingLoanApplicationDto
    ){
        return loanService.createLoanApplication(userId, incomingLoanApplicationDto);
    }

    @PostMapping(path = "/loanApplication/{loanApplicationId}")
    public OutgoingLoanDecisionDto createLoanApplicationDecision(
            @PathVariable(value = "loanApplicationId") long applicationId,
            @Validated @RequestBody IncomingApplicationDecisionDto incomingLoanDecisionDto
    ){
        return loanService.createLoanApplicationDecision(applicationId, incomingLoanDecisionDto);
    }
}
