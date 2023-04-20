package dev.n1t.account.controller;

import dev.n1t.account.dto.IncomingCreditCardTypeDto;
import dev.n1t.account.service.CreditCardTypeService;
//import dev.n1t.account.service.DummyDataInitializer;
import dev.n1t.model.CreditCardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/creditCardTypes")
    public List<CreditCardType> getCreditCardTypes(){
        return this.creditCardTypeService.getAllCreditCardTypes();
    }

    @PutMapping("/creditCardType/{creditCardTypeId}")
    public CreditCardType updateCreditCardType(
            @PathVariable(value = "creditCardTypeId") long creditCardTypeId,
            @Validated @RequestBody IncomingCreditCardTypeDto incomingCreditCardTypeDto
    ){
        return this.creditCardTypeService.updateCreditCardType(creditCardTypeId, incomingCreditCardTypeDto);
    }

    @PostMapping("/creditCardType")
    public ResponseEntity<CreditCardType> createCreditCardType(@Validated @RequestBody IncomingCreditCardTypeDto incomingCreditCardTypeDto){
        return new ResponseEntity<>(this.creditCardTypeService.createCreateCreditCardType(incomingCreditCardTypeDto), HttpStatus.CREATED);
    }

}
