package dev.n1t.account.service;

import dev.n1t.account.dto.OutgoingAbstractApplicationPrefillDto;
import dev.n1t.account.exception.UserNotFoundException;
import dev.n1t.account.repository.UserRepository;
import dev.n1t.model.Address;
import dev.n1t.model.Role;
import dev.n1t.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractApplicationServiceTest {

    @InjectMocks
    private AbstractApplicationService abstractApplicationService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getApplicationDataPrefill_userExists_returnsDto() {
        long userId = 1L;
        Address address = new Address(1L, "City", "State", "Street", "12345");
        User user = new User(
                userId,
                "John",
                "Doe",
                "john.doe@example.com",
                true,
                "password",
                true,
                1000000000L,
                address,
                new Role(1L, "ROLE_USER")
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        OutgoingAbstractApplicationPrefillDto result = abstractApplicationService.getApplicationDataPrefill(userId);

        assertEquals(address, result.getAddress());
    }

    @Test
    void getApplicationDataPrefill_userNotFound_throwsUserNotFoundException() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> abstractApplicationService.getApplicationDataPrefill(userId));
    }
}