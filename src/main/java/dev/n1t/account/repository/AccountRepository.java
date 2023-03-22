package dev.n1t.account.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import dev.n1t.model.Account;
import dev.n1t.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByUser(User user);
    @Query("SELECT a FROM Account a WHERE "
            + "(:id IS NULL OR a.id = :id) "
            + "AND (:firstName IS NULL OR a.user.firstname = :firstName) "
            + "AND (:lastName IS NULL OR a.user.lastname = :lastName) "
            + "AND (:accountTypeId IS NULL OR a.accountType.id = :accountTypeId) "
            + "AND (:active IS NULL OR a.active = :active) "
            + "AND (:accountName IS NULL OR a.accountName = :accountName) "
            + "AND (:createdDate IS NULL OR a.createdDate = :createdDate)")
    List<Account> findAllByQueryParams(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("accountTypeId") Long accountTypeId,
            @Param("active") Boolean active,
            @Param("accountName") String accountName,
            @Param("createdDate") Long createdDate
    );

}
