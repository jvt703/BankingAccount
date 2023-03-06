package dev.n1t.account.exception;

public class CreditCardTypeNotFoundException extends RuntimeException {
    public CreditCardTypeNotFoundException(long creditCardTypeId){
        super(String.format("Credit card type of id %d does not exist", creditCardTypeId));
    }
}
