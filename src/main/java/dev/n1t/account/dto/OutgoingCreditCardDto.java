package dev.n1t.account.dto;

import dev.n1t.model.Account;
import dev.n1t.model.CreditCardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutgoingCreditCardDto {
    private long accountId;
    private String rewardsName;
    private double interestRate;
    private double minimumPayment;
    private double creditLimit;

    public OutgoingCreditCardDto(CreditCardType creditCardType, Account account){
        accountId = account.getId();
        rewardsName = creditCardType.getRewardsName();
        interestRate = creditCardType.getInterestRate();
        minimumPayment = creditCardType.getMinimumPayment();
        creditLimit = creditCardType.getCreditLimit();
    }
}
