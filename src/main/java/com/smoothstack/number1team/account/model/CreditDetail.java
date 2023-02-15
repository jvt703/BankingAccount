package com.smoothstack.number1team.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "creditdetail")
public class CreditDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "accountId")
    private Long accountId;

    @Column(nullable = false, name = "interestRate")
    private double interestRate;

    @Column(nullable = false, name = "minimumPayment")
    private double minimumPayment;

    @Column(nullable = false, name = "creditLimit")
    private double creditLimit;

    @Column(nullable = false, name = "rewardsType") //TODO: see if this should be nullable
    private Long rewardsType; //TODO: see about clarifying in the name that this is an id
}
