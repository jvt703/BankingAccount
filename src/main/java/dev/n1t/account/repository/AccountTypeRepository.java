package dev.n1t.account.repository;

import dev.n1t.model.AccountType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends CrudRepository<AccountType, Long> {
}
