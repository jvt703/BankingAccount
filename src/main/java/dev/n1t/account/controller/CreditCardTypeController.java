package dev.n1t.account.controller;

import dev.n1t.account.dto.IncomingCreditCardTypeDto;
import dev.n1t.account.service.CreditCardTypeService;
//import dev.n1t.account.service.DummyDataInitializer;
import dev.n1t.model.CreditCardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class CreditCardTypeController {

    private final CreditCardTypeService creditCardTypeService;

    @Autowired
    public CreditCardTypeController(CreditCardTypeService creditCardTypeService){
        this.creditCardTypeService = creditCardTypeService;
    }

    //todo: delete the following line
//    @Autowired DummyDataInitializer dummyDataInitializer;

    @GetMapping("/creditCardTypes/{userId}")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public List<CreditCardType> getCreditCardTypes(
            @PathVariable(value = "userId") long userId,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId){
        return this.creditCardTypeService.getAllCreditCardTypes();
    }

    @PutMapping("/creditCardType/{creditCardTypeId}")
    @PreAuthorize("((#Role.equals('admin'))")
    public CreditCardType updateCreditCardType(
            @PathVariable(value = "creditCardTypeId") long creditCardTypeId,
            @Validated @RequestBody IncomingCreditCardTypeDto incomingCreditCardTypeDto,
            @RequestHeader("Role") String Role
    ){
        return this.creditCardTypeService.updateCreditCardType(creditCardTypeId, incomingCreditCardTypeDto);
    }

    @PostMapping("/creditCardType")
    @PreAuthorize("((#Role.equals('admin'))")
    public ResponseEntity<CreditCardType> createCreditCardType(
            @Validated @RequestBody IncomingCreditCardTypeDto incomingCreditCardTypeDto,
            @RequestHeader("Role") String Role){
        return new ResponseEntity<>(this.creditCardTypeService.createCreateCreditCardType(incomingCreditCardTypeDto), HttpStatus.CREATED);
    }

}
