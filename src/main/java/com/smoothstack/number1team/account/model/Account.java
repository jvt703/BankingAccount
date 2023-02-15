package com.smoothstack.number1team.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "accountTypeId", nullable = false)
    private Long accountTypeId;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Column(name = "confirmation")
    private boolean confirmation; //TODO: POSSIBLY UNUSED

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "pointsBalance")
    private Long pointsBalance;

    @Column(name = "accountName", nullable = false)
    private String accountName;

    @Column(name = "createdDate", nullable = false)
    private Long createdDate;
}
