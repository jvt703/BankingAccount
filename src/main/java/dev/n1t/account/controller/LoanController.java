package dev.n1t.account.controller;

import dev.n1t.account.dto.*;
import dev.n1t.account.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
public class LoanController {

    private final LoanService loanService;

    @Autowired()
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @PostMapping(path = "/user/{userId}/loanApplication", produces = "application/json")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public ResponseEntity<OutgoingApplicationDto> createLoanApplication(
            @PathVariable(value = "userId") long userId,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId,
            @Validated @RequestBody IncomingLoanApplicationDto incomingLoanApplicationDto
    ){
        return new ResponseEntity<>(loanService.createLoanApplication(userId, incomingLoanApplicationDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/loanApplications", produces = "application/json")
    @PreAuthorize("((#Role.equals('admin'))")
    public List<OutgoingLoanApplicationDto> getAllLoanApplications(
            @RequestParam Map<String, String> queryParams,
            @RequestHeader("Role") String Role){
        return loanService.getAllLoanApplications(queryParams);
    }

    @PostMapping(path = "/loanApplication/{loanApplicationId}")
    @PreAuthorize("((#Role.equals('admin'))")
    public ResponseEntity<OutgoingLoanDecisionDto> createLoanApplicationDecision(
            @PathVariable(value = "loanApplicationId") long applicationId,
            @Validated @RequestBody IncomingApplicationDecisionDto incomingLoanDecisionDto,
            @RequestHeader("Role") String Role
    ){
        return new ResponseEntity<>(loanService.createLoanApplicationDecision(applicationId, incomingLoanDecisionDto), HttpStatus.CREATED);
    }
}
