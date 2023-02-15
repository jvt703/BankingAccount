package com.smoothstack.number1team.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "loandetail")
public class LoanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "accountId")
    private Long accountId;

    @Column(nullable = false, name = "interestRate")
    private double interestRate;

    @Column(nullable = false, name = "minimumPayment")
    private double minimumPayment;

    @Column(nullable = false, name = "initialLoanAmount")
    private double initialLoanAmount;
}
