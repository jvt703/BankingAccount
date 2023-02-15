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
    double id;

    @Column(name = "sourceAccount")
    Account sourceAccount;

    @Column(nullable = false, name = "destinationAccount")
    Account destinationAccount;

    @Column(nullable = false)
    Long date;
}
