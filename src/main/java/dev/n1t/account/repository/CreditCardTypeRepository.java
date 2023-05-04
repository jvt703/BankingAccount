package dev.n1t.account.repository;

import dev.n1t.model.CreditCardType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreditCardTypeRepository extends CrudRepository<CreditCardType, Long> {
    List<CreditCardType> findAll();
    List<CreditCardType> findAllBySignUpEnabled(boolean signUpEnabled);
}
