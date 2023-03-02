package dev.n1t.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRegistrationDto {
    private Long accountTypeId;
    private String accountName;
}
