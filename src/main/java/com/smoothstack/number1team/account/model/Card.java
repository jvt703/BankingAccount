package com.smoothstack.number1team.account.model;

public class Card {
    Long id;
    Long accountId;
    boolean active;
    int pin;
    String cardholderName; //TODO: ask about normalization (1 user to every card, user stores name)
    Long cardNumber;
}
