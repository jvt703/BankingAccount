package dev.n1t.account.exception;

public class UserHasAlreadyAppliedForCardOfTypeException extends RuntimeException {
    public UserHasAlreadyAppliedForCardOfTypeException(long userId, String creditCardRewardsName){
        super(String.format("You (user id %d) have already applied for a %s credit card", userId, creditCardRewardsName));
    }
}
