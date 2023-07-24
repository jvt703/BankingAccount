package dev.n1t.account.dto;

import dev.n1t.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutgoingAccountEmailDto {
    private Long id;
    private long userId;
    private String email;

    private String accountTypeName;
    private String accountTypeDescription;

    private double balance;
    private boolean active;
    private long pointsBalance;
    private String accountName;
    private long createdDate;

    public OutgoingAccountEmailDto(Account account) {
        this.id = account.getId();
        this.userId = account.getUser().getId();
        this.email = account.getUser().getEmail();
        this.accountTypeName = account.getAccountType().getAccountTypeName();
        this.accountTypeDescription = account.getAccountType().getDescription();
        this.active = account.getActive();
        this.balance = account.getBalance();
        this.pointsBalance = account.getPointsBalance();
        this.accountName = account.getAccountName();
        this.createdDate = account.getCreatedDate();
    }

}
