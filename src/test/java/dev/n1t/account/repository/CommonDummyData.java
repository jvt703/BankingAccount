package dev.n1t.account.repository;

import dev.n1t.model.Address;
import dev.n1t.model.Role;
import dev.n1t.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CommonDummyData {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    private final User testUser;

    @Autowired
    public CommonDummyData(
        RoleRepository roleRepository,
        UserRepository userRepository,
        AddressRepository addressRepository
    ){
        this.roleRepository = roleRepository;
        roleRepository.deleteAll();
        Role inputUserRole = new Role();
        inputUserRole.setRoleName("Testing");
        Role outputUserRole = roleRepository.save(inputUserRole);


        this.addressRepository = addressRepository;
        addressRepository.deleteAll();
        Address inputUserAddress = new Address();
        inputUserAddress.setCity("Test City");
        inputUserAddress.setZipCode("00000");
        inputUserAddress.setState("Iowa");
        inputUserAddress.setStreet("2000 Test Avenue");
        Address outputUserAddress = addressRepository.save(inputUserAddress);

        this.userRepository = userRepository;
        userRepository.deleteAll();
        User inputUser = new User();
        inputUser.setActive(true);
        inputUser.setEmail("test@testing.test");
        inputUser.setAddress(outputUserAddress);
        inputUser.setFirstname("Testathan");
        inputUser.setLastname("Testman");
        inputUser.setRole(outputUserRole);
        inputUser.setEmailValidated(false);
        inputUser.setPassword("T3$71N6!");

        testUser = userRepository.save(inputUser);
    }
}
