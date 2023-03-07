package dev.n1t.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingLoanDto {
    private double loanAmount;
    private long debitedAccountId;
}
