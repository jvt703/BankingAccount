package dev.n1t.account.repository;

import org.springframework.data.repository.CrudRepository;
import dev.n1t.model.Account;
import dev.n1t.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByUser(User user);
}
