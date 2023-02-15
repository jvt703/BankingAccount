package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    Long id;
    Long accountId;
    boolean active;
    int pin;
    Long cardNumber;
}
