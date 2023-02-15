package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    double id;
    double sourceAccount;
    double destinationAccount;
    Long date;
}
