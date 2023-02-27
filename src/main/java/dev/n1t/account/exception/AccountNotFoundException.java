package dev.n1t.account.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(long accountId) {
        super(String.format("Account with id %d not found", accountId));
    }
}
