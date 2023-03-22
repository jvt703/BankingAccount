package dev.n1t.account.service;

import dev.n1t.account.dto.*;
import dev.n1t.account.exception.*;
import dev.n1t.account.repository.*;
import dev.n1t.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreditCardService {

    private final CreditCardApplicationRepository creditCardApplicationRepository;

    private final ApplicationDetailsRepository applicationDetailsRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final CreditCardTypeRepository creditCardTypeRepository;
    private final UserRepository userRepository;
    private final CreditDetailRepository creditDetailRepository;

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

    public OutgoingApplicationDto createCreditCardApplication(IncomingCreditCardApplicationDto incomingCreditCardApplicationDto, long userId){
        Optional<CreditCardType> creditCardType = creditCardTypeRepository.findById(incomingCreditCardApplicationDto.getCreditCardTypeId());
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            if(creditCardType.isPresent()){
                List<Account> userAccounts = accountRepository.findByUser(user.get());
                Optional<CreditDetail> creditDetail = creditDetailRepository.findByCreditCardTypeAndAccountIn(creditCardType.get(), userAccounts);

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

                        return new OutgoingApplicationDto(String.format("You have successfully applied for the %s card!", creditCardType.get().getRewardsName()));
                    } else throw new UserHasAlreadyAppliedForCardOfTypeException(user.get().getId(), creditCardType.get().getRewardsName());
                } else throw new UserAlreadyHasCardOfTypeException(user.get().getId(), creditCardType.get().getRewardsName());
            } else throw new CreditCardTypeNotFoundException(incomingCreditCardApplicationDto.getCreditCardTypeId());
        } else throw new UserNotFoundException(userId);
    }
    public OutgoingCreditCardDecisionDto createCreditCardApplicationDecision(long creditCardApplicationId, IncomingApplicationDecisionDto incomingApplicationDecisionDto){
        Optional<CreditCardApplication> creditCardApplication = creditCardApplicationRepository.findById(creditCardApplicationId);

        if(creditCardApplication.isPresent()){

            ApplicationDetails creditCardApplicationDetails = creditCardApplication.get().getApplicationDetails();
            if(creditCardApplicationDetails.getDecisionDate() == null){
                if(incomingApplicationDecisionDto.isApproved()){
                    Account inputAccount = Account.builder()
                            .createdDate(new Date().getTime())
                            .user(creditCardApplication.get().getApplicationDetails().getUser())
                            .balance(0.0)
                            .active(true)
                            .confirmation(false)
                            .pointsBalance(0L)
                            .accountName(creditCardApplication.get().getCreditCardType().getRewardsName() + " Card")
                            .accountType(accountTypeRepository.findByAccountTypeName("Credit"))
                            .build();
                    Account outputAccount = accountRepository.save(inputAccount);

                    CreditDetail inputCreditDetail = new CreditDetail();
                    inputCreditDetail.setCreditCardType(creditCardApplication.get().getCreditCardType());
                    inputCreditDetail.setAccount(outputAccount);

                    creditDetailRepository.save(inputCreditDetail);
                }

                creditCardApplicationDetails.setApproved(incomingApplicationDecisionDto.isApproved());
                creditCardApplicationDetails.setDecisionDate(Instant.now());
                applicationDetailsRepository.save(creditCardApplicationDetails);

                return new OutgoingCreditCardDecisionDto(
                        creditCardApplicationDetails.getUser().getFirstname(),
                        creditCardApplicationDetails.getUser().getLastname(),
                        creditCardApplication.get().getCreditCardType().getRewardsName(),
                        incomingApplicationDecisionDto.isApproved()
                );
            } else throw new ApplicationDecisionAlreadyMadeException(creditCardApplicationDetails);
        } else throw new CreditCardApplicationNotFoundException(creditCardApplicationId);
    }

    public List<OutgoingCreditCardApplicationDto> getAllCreditCardApplications(){
        return creditCardApplicationRepository.findAll().stream()
                .map(OutgoingCreditCardApplicationDto::new)
                .collect(Collectors.toList());
    }
}
