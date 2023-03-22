package dev.n1t.account.dto;

import dev.n1t.model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OutgoingLoanApplicationDto {
    private Long loanApplicationId;
    private Long depositAccountId;
    private String depositAccountName;
    private Double loanAmount;
    private String userFirstName;
    private String userLastName;
    private Long userDOB;
    private String userEmailAddress;
    private Address userAddress;
    private Long socialSecurityNumber;
    private String motherMaidenName;
    private ResidenceOwnershipStatus residenceOwnershipStatus;
    private double housingPayment;
    private EmploymentStatus employmentStatus;
    private double grossAnnualIncome;
    private boolean approved;
    private Instant decisionDate;

    public OutgoingLoanApplicationDto(LoanApplication loanApplication){
        loanApplicationId = loanApplication.getId();
        ApplicationDetails loanApplicationDetails = loanApplication.getApplicationDetails();
        User user = loanApplicationDetails.getUser();
        Account depositAccount = loanApplication.getDebitedAccount();

        depositAccountId = depositAccount.getId();
        depositAccountName = depositAccount.getAccountName();
        loanAmount = loanApplication.getRequestedAmount();
        userFirstName = user.getFirstname();
        userLastName = user.getLastname();
        userDOB = user.getBirthDate();
        userAddress = user.getAddress();
        userEmailAddress = user.getEmail();
        socialSecurityNumber = loanApplicationDetails.getSocialSecurityNumber();
        motherMaidenName = loanApplicationDetails.getMotherMaidenName();
        residenceOwnershipStatus = loanApplicationDetails.getResidenceOwnershipStatus();
        housingPayment = loanApplicationDetails.getHousingPayment();
        employmentStatus = loanApplicationDetails.getEmploymentStatus();
        grossAnnualIncome = loanApplicationDetails.getGrossAnnualIncome();
        approved = loanApplicationDetails.isApproved();
        decisionDate = loanApplicationDetails.getDecisionDate();
    }

}
