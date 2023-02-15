package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDetail {
    Long id;
    Long accountId;
    int interestRate; //TODO: ask why this is different from cardDetail interestRate (decimal)
    double minimumPayment;
    double initialLoanAmount;
}
