package com.smoothstack.number1team.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accountId", nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private int pin;

    @Column(name = "cardNumber", nullable = false)
    private Long cardNumber;
}
