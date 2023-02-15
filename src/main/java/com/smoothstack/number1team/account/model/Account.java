package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private Long id;
    private Long userId;
    private Long accountTypeId;
    private double balance;
    private boolean confirmation; //TODO: POSSIBLY UNUSED
    private boolean active;
    private Long pointsBalance;
    private String accountName;
    private Long createdDate;
}
