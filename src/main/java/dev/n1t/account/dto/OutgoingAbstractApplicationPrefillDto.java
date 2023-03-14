package dev.n1t.account.dto;

import dev.n1t.model.Address;
import dev.n1t.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutgoingAbstractApplicationPrefillDto {
    private Address address;
    private String firstname;
    private String lastname;
    private String email;
    private Long birthDate;

    public OutgoingAbstractApplicationPrefillDto(User user){
        this.address = user.getAddress();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
    }
}
