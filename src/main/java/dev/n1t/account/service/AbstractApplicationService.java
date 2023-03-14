package dev.n1t.account.service;

import dev.n1t.account.dto.OutgoingAbstractApplicationPrefillDto;
import dev.n1t.account.exception.UserNotFoundException;
import dev.n1t.account.repository.UserRepository;
import dev.n1t.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AbstractApplicationService {

    private final UserRepository userRepository;

    @Autowired
    public AbstractApplicationService(
        UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    public OutgoingAbstractApplicationPrefillDto getApplicationDataPrefill(long userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            return new OutgoingAbstractApplicationPrefillDto(user.get());
        } else throw new UserNotFoundException(userId);
    }
}
