package dev.n1t.account.exception;

public class UserHasAlreadyAppliedForCardOfTypeException extends RuntimeException {
    public UserHasAlreadyAppliedForCardOfTypeException(long userId, long creditCardTypeId){
        super(String.format("User of id %d has already applied for a card of type id %s", userId, creditCardTypeId));
    }
}
