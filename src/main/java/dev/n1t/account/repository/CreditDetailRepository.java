package dev.n1t.account.repository;

import dev.n1t.model.Account;
import dev.n1t.model.CreditCardType;
import dev.n1t.model.CreditDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CreditDetailRepository extends CrudRepository<CreditDetail, Long> {
    Optional<CreditDetail> findByCreditCardTypeAndAccountIn(CreditCardType creditCardType, List<Account> accounts);
}
