package dev.n1t.account.dto;


import dev.n1t.model.Account;

public class OutgoingAccountDto {
    private final long id;
    private final long userId;
    private final long accountTypeId;
    private final double balance;
    private final boolean confirmation;
    private final boolean active;
    private final long pointsBalance;
    private final String accountName;
    private final long createdDate;

    public OutgoingAccountDto(Account account) {
        this.id = account.getId();
        this.userId = account.getUser().getId();
        this.accountTypeId = account.getAccountType().getId();
        this.balance = account.getBalance();
        this.confirmation = account.isConfirmation(); //todo: maybe change name so lombok getter name makes sense
        this.active = account.isActive();
        this.pointsBalance = account.getPointsBalance();
        this.accountName = account.getAccountName();
        this.createdDate = account.getCreatedDate();
    }
}