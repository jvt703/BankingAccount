package dev.n1t.account.exception;

import dev.n1t.model.ApplicationDetails;

import java.time.ZoneId;

public class ApplicationDecisionAlreadyMadeException extends RuntimeException {
    public ApplicationDecisionAlreadyMadeException(ApplicationDetails applicationDetails){
        super(String.format("Application %d approval was set to %b on %s", applicationDetails.getId(), applicationDetails.getApproved(), applicationDetails.getDecisionDate().atZone(ZoneId.systemDefault())));
    }
}
