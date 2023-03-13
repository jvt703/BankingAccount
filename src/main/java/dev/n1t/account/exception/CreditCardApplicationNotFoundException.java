package dev.n1t.account.exception;

public class CreditCardApplicationNotFoundException extends RuntimeException {
    public CreditCardApplicationNotFoundException(long creditCardApplicationId){
        super(String.format("Credit card application of id %d not found", creditCardApplicationId));
    }
}
