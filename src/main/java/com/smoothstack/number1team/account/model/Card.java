package com.smoothstack.number1team.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private Long id;
    private Long accountId;
    private boolean active;
    private int pin;
    private Long cardNumber;
}
