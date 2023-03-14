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

    @PostMapping(path = "/user/{userId}/loanApplication", produces = "application/json")
    public OutgoingApplicationDto createLoanApplication(
            @PathVariable(value = "userId") long userId,
            @Validated @RequestBody IncomingLoanApplicationDto incomingLoanApplicationDto
    ){
        return loanService.createLoanApplication(userId, incomingLoanApplicationDto);
    }

    @GetMapping(path = "loanApplications", produces = "application/json")
    public List<OutgoingLoanApplicationDto> getAllLoanApplications(){
        return loanService.getAllLoanApplications();
    }

    @PostMapping(path = "/loanApplication/{loanApplicationId}")
    public OutgoingLoanDecisionDto createLoanApplicationDecision(
            @PathVariable(value = "loanApplicationId") long applicationId,
            @Validated @RequestBody IncomingApplicationDecisionDto incomingLoanDecisionDto
    ){
        return loanService.createLoanApplicationDecision(applicationId, incomingLoanDecisionDto);
    }
}
