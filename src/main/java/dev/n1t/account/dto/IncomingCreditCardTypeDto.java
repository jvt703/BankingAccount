package dev.n1t.account.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IncomingCreditCardTypeDto { //todo: limit access to admins only
    private String rewardsName;
    private double interestRate;
    private double minimumPayment;
    private double creditLimit;

}


