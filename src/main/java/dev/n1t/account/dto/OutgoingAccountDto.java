package dev.n1t.account.dto;


import dev.n1t.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutgoingAccountDto {
    private long id;
    private long userId;

    private String accountTypeName;
    private String accountTypeDescription;

    private double balance;
    private boolean confirmation;
    private boolean active;
    private long pointsBalance;
    private String accountName;
    private long createdDate;

    public OutgoingAccountDto(Account account) {
        this.id = account.getId();
        this.userId = account.getUser().getId();
        this.accountTypeName = account.getAccountType().getAccountTypeName();
        this.accountTypeDescription = account.getAccountType().getDescription();
        this.balance = account.getBalance();
        this.confirmation = account.isConfirmation(); //todo: maybe change name so lombok getter name makes sense
        this.active = account.isActive();
        this.pointsBalance = account.getPointsBalance();
        this.accountName = account.getAccountName();
        this.createdDate = account.getCreatedDate();
    }
}