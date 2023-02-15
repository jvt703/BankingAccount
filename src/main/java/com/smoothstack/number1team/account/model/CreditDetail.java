package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditDetail {
    private Long id;
    private Long accountId;
    private double interestRate;
    private double minimumPayment;
    private double creditLimit;
    private Long rewardsType;
}
