package dev.n1t.account.repository;

import dev.n1t.model.ApplicationDetails;
import dev.n1t.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationDetailsRepository extends CrudRepository<ApplicationDetails, Long> {
    List<ApplicationDetails> getByUser(User user);
}
