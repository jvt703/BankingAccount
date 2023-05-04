package dev.n1t.account.exception;

public class CreditCardSignUpDisabledException extends RuntimeException {
    public CreditCardSignUpDisabledException(long id){
        super(String.format("Signup for credit card type %d has been disabled.", id));
    }
}
