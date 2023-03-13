package dev.n1t.account.controller;

import dev.n1t.account.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotFoundException(AccountNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = AccountTypeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountTypeNotFoundException(AccountTypeNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = CreditCardTypeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCreditCardTypeNotFoundException(CreditCardTypeNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = CreditCardApplicationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCreditCardApplicationNotFoundException(CreditCardApplicationNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = LoanApplicationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLoanApplicationNotFoundException(LoanApplicationNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = AccountDoesNotBelongToUserException.class)
    public ResponseEntity<Map<String, String>> handleAccountDoesNotBelongToUserException(AccountDoesNotBelongToUserException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @ExceptionHandler(value = UserAlreadyHasCardOfTypeException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyHasCardOfTypeException(UserAlreadyHasCardOfTypeException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(value = UserHasAlreadyAppliedForCardOfTypeException.class)
    public ResponseEntity<Map<String, String>> handleUserHasAlreadyAppliedForCardOfTypeException(UserHasAlreadyAppliedForCardOfTypeException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(value = ApplicationDecisionAlreadyMadeException.class)
    public ResponseEntity<Map<String, String>> handleApplicationDecisionAlreadyMadeException(ApplicationDecisionAlreadyMadeException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(value = InvalidDebitedAccountTypeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDebitedAccountTypeException(InvalidDebitedAccountTypeException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    //todo: refactor repetitive code?
}
