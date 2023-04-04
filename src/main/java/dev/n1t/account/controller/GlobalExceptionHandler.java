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
    public ResponseEntity<Map<String, String>> exceptionResponse(RuntimeException e, HttpStatus status){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<Map<String, String>> notFoundException(RuntimeException e){
        return exceptionResponse(e, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Map<String, String>> conflictException(RuntimeException e){
        return exceptionResponse(e, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e){
        return notFoundException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotFoundException(AccountNotFoundException e){
        return notFoundException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = AccountTypeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountTypeNotFoundException(AccountTypeNotFoundException e){
        return notFoundException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = CreditCardTypeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCreditCardTypeNotFoundException(CreditCardTypeNotFoundException e){
        return notFoundException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = CreditCardApplicationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCreditCardApplicationNotFoundException(CreditCardApplicationNotFoundException e){
        return notFoundException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = LoanApplicationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLoanApplicationNotFoundException(LoanApplicationNotFoundException e){
        return notFoundException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = AccountDoesNotBelongToUserException.class)
    public ResponseEntity<Map<String, String>> handleAccountDoesNotBelongToUserException(AccountDoesNotBelongToUserException e){
        return exceptionResponse(e, HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @ExceptionHandler(value = UserAlreadyHasCardOfTypeException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyHasCardOfTypeException(UserAlreadyHasCardOfTypeException e){
        return conflictException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = UserHasAlreadyAppliedForCardOfTypeException.class)
    public ResponseEntity<Map<String, String>> handleUserHasAlreadyAppliedForCardOfTypeException(UserHasAlreadyAppliedForCardOfTypeException e){
        return conflictException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = ApplicationDecisionAlreadyMadeException.class)
    public ResponseEntity<Map<String, String>> handleApplicationDecisionAlreadyMadeException(ApplicationDecisionAlreadyMadeException e){
        return conflictException(e);
    }

    @ResponseBody
    @ExceptionHandler(value = InvalidDebitedAccountTypeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDebitedAccountTypeException(InvalidDebitedAccountTypeException e){
        return exceptionResponse(e, HttpStatus.BAD_REQUEST);
    }
}
