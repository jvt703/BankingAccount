package dev.n1t.account.controller;

import dev.n1t.account.dto.IncomingLoanDto;
import dev.n1t.account.dto.OutgoingLoanDto;
import dev.n1t.account.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class LoanController {

    private final LoanService loanService;

    @Autowired()
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @PostMapping(path = "/user/{userId}/loan", produces = "application/json")
    public OutgoingLoanDto createLoan(
            @PathVariable(value = "userId") long userId,
            @Validated @RequestBody IncomingLoanDto incomingLoanDto
        ){
        return loanService.createLoan(userId, incomingLoanDto);
    }
}
