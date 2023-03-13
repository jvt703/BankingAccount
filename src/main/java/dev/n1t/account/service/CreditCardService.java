package dev.n1t.account.service;

import dev.n1t.account.dto.IncomingCreditCardApplicationDto;
import dev.n1t.account.dto.OutgoingCreditCardApplicationDto;
import dev.n1t.account.dto.OutgoingCreditCardDto;
import dev.n1t.account.exception.CreditCardTypeNotFoundException;
import dev.n1t.account.exception.UserAlreadyHasCardOfTypeException;
import dev.n1t.account.exception.UserHasAlreadyAppliedForCardOfTypeException;
import dev.n1t.account.exception.UserNotFoundException;
import dev.n1t.account.repository.*;
import dev.n1t.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreditCardService {

    private final CreditCardApplicationRepository creditCardApplicationRepository;

    private final ApplicationDetailsRepository applicationDetailsRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final CreditCardTypeRepository creditCardTypeRepository;
    private final UserRepository userRepository;
    private final CreditDetailRepository creditDetailRepository;

    private final Map<String, EmploymentStatus> employmentStatusMap = new HashMap<>();


    @Autowired
    public CreditCardService(
        CreditCardApplicationRepository creditCardApplicationRepository,
        ApplicationDetailsRepository applicationDetailsRepository,
        AccountRepository accountRepository,
        AccountTypeRepository accountTypeRepository,
        CreditCardTypeRepository creditCardTypeRepository,
        UserRepository userRepository,
        CreditDetailRepository creditDetailRepository
    ){
        this.creditCardApplicationRepository = creditCardApplicationRepository;
        this.applicationDetailsRepository = applicationDetailsRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.creditCardTypeRepository = creditCardTypeRepository;
        this.userRepository = userRepository;
        this.creditDetailRepository = creditDetailRepository;
    }

    public OutgoingCreditCardApplicationDto createCreditCardApplication(IncomingCreditCardApplicationDto incomingCreditCardApplicationDto, long userId){
        Optional<CreditCardType> creditCardType = creditCardTypeRepository.findById(incomingCreditCardApplicationDto.getCreditCardTypeId());
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            if(creditCardType.isPresent()){
                List<Account> userAccounts = accountRepository.findByUser(user.get());
                Optional<CreditDetail> creditDetail = creditDetailRepository.findByCreditCardTypeAndAccountIn(creditCardType.get(), userAccounts);

                //todo: handle duplicate applications
                if(creditDetail.isEmpty()){
                    List<ApplicationDetails> userApplicationDetails = applicationDetailsRepository.getByUser(user.get());
                    Optional<CreditCardApplication> creditCardApplication = creditCardApplicationRepository.getByCreditCardTypeAndApplicationDetailsIn(creditCardType.get(), userApplicationDetails);

                    if(creditCardApplication.isEmpty()){
                        ApplicationDetails inputApplicationDetails = ApplicationDetails.builder()
                                .employmentStatus(incomingCreditCardApplicationDto.getEmploymentStatus())
                                .residenceOwnershipStatus(incomingCreditCardApplicationDto.getResidenceOwnershipStatus())
                                .grossAnnualIncome(incomingCreditCardApplicationDto.getGrossAnnualIncome())
                                .housingPayment(incomingCreditCardApplicationDto.getHousingPayment())
                                .motherMaidenName(incomingCreditCardApplicationDto.getMotherMaidenName())
                                .socialSecurityNumber(incomingCreditCardApplicationDto.getSocialSecurityNumber())
                                .user(user.get())
                                .build();

                        ApplicationDetails outputApplicationDetails = applicationDetailsRepository.save(inputApplicationDetails);

                        CreditCardApplication inputCreditCardApplication = CreditCardApplication.builder()
                                .applicationDetails(outputApplicationDetails)
                                .creditCardType(creditCardType.get())
                                .build();

                        creditCardApplicationRepository.save(inputCreditCardApplication);

                        return new OutgoingCreditCardApplicationDto(String.format("You have successfully applied for the %s card!", creditCardType.get().getRewardsName()));
                    } else throw new UserHasAlreadyAppliedForCardOfTypeException(user.get().getId(), creditCardType.get().getId());
                } else throw new UserAlreadyHasCardOfTypeException(user.get().getId(), creditCardType.get().getId());
            } else throw new CreditCardTypeNotFoundException(incomingCreditCardApplicationDto.getCreditCardTypeId());
        } else throw new UserNotFoundException(userId);
    }
    public OutgoingCreditCardDto createCreditCard(IncomingCreditCardApplicationDto incomingCreditCardApplicationDto, long userId){
        Optional<CreditCardType> creditCardType = creditCardTypeRepository.findById(incomingCreditCardApplicationDto.getCreditCardTypeId());
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
                    inputAccount.setAccountType(accountTypeRepository.findByAccountTypeName("Credit")); //credit card account type

                    Account outputAccount = accountRepository.save(inputAccount);

                    CreditDetail inputCreditDetail = new CreditDetail();
                    inputCreditDetail.setCreditCardType(creditCardType.get());
                    inputCreditDetail.setAccount(outputAccount);

                    creditDetailRepository.save(inputCreditDetail);

                    return new OutgoingCreditCardDto(creditCardType.get(), outputAccount);
                }
            } else throw new CreditCardTypeNotFoundException(incomingCreditCardApplicationDto.getCreditCardTypeId());
        } else throw new UserNotFoundException(userId);
    }
}
