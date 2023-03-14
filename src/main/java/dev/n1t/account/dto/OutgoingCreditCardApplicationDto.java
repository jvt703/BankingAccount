package dev.n1t.account.dto;

import dev.n1t.model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OutgoingCreditCardApplicationDto {
    private Long creditCardApplicationId;
    private String creditCardRewardsName;
    private Long creditCardTypeId;

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

    public OutgoingCreditCardApplicationDto(CreditCardApplication creditCardApplication){
        creditCardApplicationId = creditCardApplication.getId();
        ApplicationDetails creditCardApplicationDetails = creditCardApplication.getApplicationDetails();
        User user = creditCardApplicationDetails.getUser();
        CreditCardType creditCardType = creditCardApplication.getCreditCardType();

        creditCardTypeId = creditCardType.getId();
        creditCardRewardsName = creditCardType.getRewardsName();

        userFirstName = user.getFirstname();
        userLastName = user.getLastname();
        userDOB = user.getBirthDate();
        userAddress = user.getAddress();
        userEmailAddress = user.getEmail();
        socialSecurityNumber = creditCardApplicationDetails.getSocialSecurityNumber();
        motherMaidenName = creditCardApplicationDetails.getMotherMaidenName();
        residenceOwnershipStatus = creditCardApplicationDetails.getResidenceOwnershipStatus();
        housingPayment = creditCardApplicationDetails.getHousingPayment();
        employmentStatus = creditCardApplicationDetails.getEmploymentStatus();
        grossAnnualIncome = creditCardApplicationDetails.getGrossAnnualIncome();
        approved = creditCardApplicationDetails.isApproved();
        decisionDate = creditCardApplicationDetails.getDecisionDate();
    }
}
