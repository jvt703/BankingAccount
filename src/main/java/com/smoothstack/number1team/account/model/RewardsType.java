package com.smoothstack.number1team.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rewardstype")
public class RewardsType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double id;

    @Column(nullable = false, name = "rewardsName")
    private String rewardsName;
}
