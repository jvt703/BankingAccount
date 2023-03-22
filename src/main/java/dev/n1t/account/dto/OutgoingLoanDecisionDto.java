package dev.n1t.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutgoingLoanDecisionDto {
    private OutgoingLoanApplicationDto loanApplication;
    private String debitedAccountName;
    private double debitedAccountNewBalance;
}
