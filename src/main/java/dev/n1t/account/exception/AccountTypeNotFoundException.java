package dev.n1t.account.exception;

public class AccountTypeNotFoundException extends RuntimeException {
    public AccountTypeNotFoundException(long accountTypeId) {
        super(String.format("Account type with id %d not found", accountTypeId));
    }
}
