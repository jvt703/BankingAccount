package dev.n1t.account.exception;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(long loanApplicationId){
        super(String.format("Loan application of id %d not found", loanApplicationId));
    }
}
