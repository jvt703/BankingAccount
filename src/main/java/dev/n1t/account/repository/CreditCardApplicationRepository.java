package dev.n1t.account.repository;

import dev.n1t.model.ApplicationDetails;
import dev.n1t.model.CreditCardApplication;
import dev.n1t.model.CreditCardType;
import dev.n1t.model.LoanApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreditCardApplicationRepository extends CrudRepository<CreditCardApplication, Long> {
    Optional<CreditCardApplication> getByCreditCardTypeAndApplicationDetailsIn(CreditCardType creditCardType, List<ApplicationDetails> applicationDetails);

    @Query("SELECT c FROM CreditCardApplication c WHERE (:id IS NULL OR c.id = :id) "
            + "AND (:firstName IS NULL OR c.applicationDetails.user.firstname = :firstName) "
            + "AND (:lastName IS NULL OR c.applicationDetails.user.lastname = :lastName) "
            + "AND (:approved IS NULL OR c.applicationDetails.approved = :approved) "
            + "AND (:decisionMade IS NULL OR (:decisionMade = true AND c.applicationDetails.decisionDate IS NOT NULL) "
            + "OR (:decisionMade = false AND c.applicationDetails.decisionDate IS NULL))")
    List<CreditCardApplication> findAllByQueryParams(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("approved") Boolean approved,
            @Param("decisionMade") Boolean decisionMade
    );
}
