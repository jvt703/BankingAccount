package dev.n1t.account.exception;

public class AccountDoesNotBelongToUserException extends RuntimeException {
    public AccountDoesNotBelongToUserException(long accountId, long userId) {
        super(String.format("Account with id %d does not belong to user of id %d", accountId, userId));
    }
}
