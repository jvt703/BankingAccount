package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountType {
    Long id;
    String accountType; //TODO: rename this in database
    String description;
}
