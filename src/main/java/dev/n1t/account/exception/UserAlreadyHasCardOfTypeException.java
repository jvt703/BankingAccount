package dev.n1t.account.exception;

public class UserAlreadyHasCardOfTypeException extends RuntimeException {
    public UserAlreadyHasCardOfTypeException(long userId, String creditCardRewardsName){
        super(String.format("You (user id %d) already own a %s credit card", userId, creditCardRewardsName));
    }
}
