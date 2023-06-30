package dev.n1t.account.controller;

import dev.n1t.account.dto.*;
import dev.n1t.account.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService){
        this.creditCardService = creditCardService;
    }

    @PostMapping("/user/{userId}/creditCardApplication")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public ResponseEntity<OutgoingApplicationDto> createCreditCardApplication(
        @Validated @RequestBody IncomingCreditCardApplicationDto incomingCreditCardApplicationDto,
        @PathVariable(value = "userId") long userId,
        @RequestHeader("id") Long requestId,
        @RequestHeader("Role") String Role
    ){
        return new ResponseEntity<>(creditCardService.createCreditCardApplication(incomingCreditCardApplicationDto, userId), HttpStatus.CREATED);
    }

    @PostMapping("/creditCardApplication/{creditCardApplicationId}")
    @PreAuthorize("((#Role.equals('admin'))")
    public ResponseEntity<OutgoingCreditCardApplicationDto> createCreditCardApplicationDecision(
            @PathVariable(value = "creditCardApplicationId") long creditCardApplicationId,
            @Validated @RequestBody IncomingApplicationDecisionDto incomingApplicationDecisionDto,
            @RequestHeader("Role") String Role
    ){
        return new ResponseEntity<>(creditCardService.createCreditCardApplicationDecision(creditCardApplicationId, incomingApplicationDecisionDto), HttpStatus.CREATED);
    }

    @GetMapping("/creditCardApplications")
    @PreAuthorize("((#Role.equals('admin'))")
    public List<OutgoingCreditCardApplicationDto> getAllCreditCardApplications(
            @RequestParam Map<String, String> queryParams,
            @RequestHeader("Role") String Role){
        return creditCardService.getAllCreditCardApplications(queryParams);
    }
}
