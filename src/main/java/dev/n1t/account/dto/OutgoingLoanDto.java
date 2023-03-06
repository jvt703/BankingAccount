package dev.n1t.account.dto;

import dev.n1t.model.Account;
import dev.n1t.model.LoanDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutgoingLoanDto {
    private long accountId;
    private double approvedAmount;
    private double interestRate;
    private double minimumPaymentAmount;

    public OutgoingLoanDto(LoanDetail loanDetail, Account account){
        accountId = account.getId();
        approvedAmount = loanDetail.getInitialLoanAmount();
        interestRate = loanDetail.getInterestRate();
        minimumPaymentAmount = loanDetail.getMinimumPayment();
    }

}
