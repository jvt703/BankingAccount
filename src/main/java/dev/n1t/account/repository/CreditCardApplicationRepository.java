package dev.n1t.account.repository;

import dev.n1t.model.ApplicationDetails;
import dev.n1t.model.CreditCardApplication;
import dev.n1t.model.CreditCardType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardApplicationRepository extends CrudRepository<CreditCardApplication, Long> {
    Optional<CreditCardApplication> getByCreditCardTypeAndApplicationDetailsIn(CreditCardType creditCardType, List<ApplicationDetails> applicationDetails);
}
