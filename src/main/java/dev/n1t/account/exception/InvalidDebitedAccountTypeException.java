package dev.n1t.account.exception;

public class InvalidDebitedAccountTypeException extends RuntimeException {
    public InvalidDebitedAccountTypeException(){
        super("Loan deposit account must be of type Checking or Savings");
    }
}
