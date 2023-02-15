package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    Long id;
    Long userId;
    Long accountTypeId;
    double balance;
    boolean confirmation; //TODO: ASK WHAT THIS DOES
    boolean active;
    Long pointsBalance;
    String accountName;
    Long createdDate;
}
