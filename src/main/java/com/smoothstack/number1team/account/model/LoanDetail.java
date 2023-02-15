package com.smoothstack.number1team.account.model;

public class LoanDetail {
    Long id;
    Long accountId;
    int interestRate; //TODO: ask why this is different from cardDetail interestRate (decimal)
    double minimumPayment;
    double initialLoanAmount;
}
