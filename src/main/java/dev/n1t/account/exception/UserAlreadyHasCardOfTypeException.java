package dev.n1t.account.exception;

public class UserAlreadyHasCardOfTypeException extends RuntimeException {
    public UserAlreadyHasCardOfTypeException(long userId, long accountTypeId){
        super(String.format("User of id %d already owns a card of type id %d", userId, accountTypeId));
    }
}
