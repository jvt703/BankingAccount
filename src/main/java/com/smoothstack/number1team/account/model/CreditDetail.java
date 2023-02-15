package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditDetail {
    Long id;
    Long accountId;
    double interestRate;
    double minimumPayment;
    double creditLimit;
    Long rewardsType;
}
