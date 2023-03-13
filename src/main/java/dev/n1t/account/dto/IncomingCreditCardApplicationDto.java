package dev.n1t.account.dto;

import dev.n1t.model.EmploymentStatus;
import dev.n1t.model.ResidenceOwnershipStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingCreditCardApplicationDto {
    private long creditCardTypeId;

    private long socialSecurityNumber;

    private String motherMaidenName;

    private ResidenceOwnershipStatus residenceOwnershipStatus;

    private double housingPayment;

    private EmploymentStatus employmentStatus;

    private double grossAnnualIncome;

}
