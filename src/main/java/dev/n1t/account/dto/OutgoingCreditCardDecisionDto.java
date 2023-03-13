package dev.n1t.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutgoingCreditCardDecisionDto {
    private String applicantFirstName;
    private String applicantLastName;
    private String cardRewardsName;
    private boolean approved;
}
