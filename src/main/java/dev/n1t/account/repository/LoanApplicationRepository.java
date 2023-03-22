package dev.n1t.account.repository;

import dev.n1t.model.LoanApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Long> {

    @Query("SELECT l FROM LoanApplication l WHERE (:id IS NULL OR l.id = :id) "
            + "AND (:firstName IS NULL OR l.applicationDetails.user.firstname = :firstName) "
            + "AND (:lastName IS NULL OR l.applicationDetails.user.lastname = :lastName) "
            + "AND (:approved IS NULL OR l.applicationDetails.approved = :approved) "
            + "AND (:decisionMade IS NULL OR (:decisionMade = true AND l.applicationDetails.decisionDate IS NOT NULL) "
            + "OR (:decisionMade = false AND l.applicationDetails.decisionDate IS NULL))")
    List<LoanApplication> findAllByQueryParams(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("approved") Boolean approved,
            @Param("decisionMade") Boolean decisionMade
    );

}
