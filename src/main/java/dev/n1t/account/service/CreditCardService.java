package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingCreditCardDto;
import dev.n1t.account.dto.OutgoingCreditCardDto;
import dev.n1t.account.exception.CreditCardTypeNotFoundException;
import dev.n1t.account.exception.UserAlreadyHasCardOfTypeException;
import dev.n1t.account.exception.UserNotFoundException;
import dev.n1t.account.repository.AccountRepository;
import dev.n1t.account.repository.CreditCardTypeRepository;
import dev.n1t.account.repository.CreditDetailRepository;
import dev.n1t.account.repository.UserRepository;
import dev.n1t.model.Account;
import dev.n1t.model.CreditCardType;
import dev.n1t.model.CreditDetail;
import dev.n1t.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {
    private final AccountRepository accountRepository;
    private final CreditCardTypeRepository creditCardTypeRepository;
    private final UserRepository userRepository;
    private final CreditDetailRepository creditDetailRepository;

    @Autowired
    public CreditCardService(
        AccountRepository accountRepository,
        CreditCardTypeRepository creditCardTypeRepository,
        UserRepository userRepository,
        CreditDetailRepository creditDetailRepository
    ){
        this.accountRepository = accountRepository;
        this.creditCardTypeRepository = creditCardTypeRepository;
        this.userRepository = userRepository;
        this.creditDetailRepository = creditDetailRepository;
    }

    public OutgoingCreditCardDto createCreditCard(IncomingCreditCardDto incomingCreditCardDto, long userId){
        Optional<CreditCardType> creditCardType = creditCardTypeRepository.findById(incomingCreditCardDto.getCreditCardTypeId());
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            if(creditCardType.isPresent()){
                List<Account> userAccounts = accountRepository.findByUser(user.get());
                Optional<CreditDetail> creditDetail = creditDetailRepository.findByCreditCardTypeAndAccountIn(creditCardType.get(), userAccounts);

                if(creditDetail.isPresent()){
                    throw new UserAlreadyHasCardOfTypeException(user.get().getId(), creditDetail.get().getId());
                } else {
                    Account inputAccount = new Account();
                    inputAccount.setCreatedDate(new Date().getTime());
                    inputAccount.setUser(user.get());
                    inputAccount.setBalance(0.0);
                    inputAccount.setActive(true);
                    inputAccount.setConfirmation(false);
                    inputAccount.setPointsBalance(0L);
                    inputAccount.setAccountName(creditCardType.get().getRewardsName() + " Card");
                    inputAccount.setAccountType(); //credit card account type
                    //todo: make the account types predetermined

                    Account outputAccount = accountRepository.save(inputAccount);

                    CreditDetail inputCreditDetail = new CreditDetail();
                    inputCreditDetail.setCreditCardType(creditCardType.get());
                    inputCreditDetail.setAccount(outputAccount);

                    creditDetailRepository.save(inputCreditDetail);

                    return new OutgoingCreditCardDto(creditCardType.get(), outputAccount);
                }
            } else throw new CreditCardTypeNotFoundException(incomingCreditCardDto.getCreditCardTypeId());
        } else throw new UserNotFoundException(userId);
    }
}
