package dev.n1t.account.repository;

import dev.n1t.model.LoanDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDetailRepository extends CrudRepository<LoanDetail, Long> {
}
