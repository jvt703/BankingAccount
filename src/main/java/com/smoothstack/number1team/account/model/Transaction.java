package com.smoothstack.number1team.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double id;

    @Column(name = "sourceAccount")
    private Account sourceAccount;

    @Column(nullable = false, name = "destinationAccount")
    private Account destinationAccount;

    @Column(nullable = false)
    private Long date;
}
