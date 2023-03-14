package dev.n1t.account.repository;

import dev.n1t.model.LoanApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Long> {
    List<LoanApplication> findAll();
}
