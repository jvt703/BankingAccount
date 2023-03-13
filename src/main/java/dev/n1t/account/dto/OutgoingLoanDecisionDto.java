package dev.n1t.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutgoingLoanDecisionDto {
    private String applicantFirstName;
    private String applicantLastName;
    private double loanAmount;
    private String debitedAccountName;
    private double debitedAccountNewBalance;
    private boolean approved;
}
