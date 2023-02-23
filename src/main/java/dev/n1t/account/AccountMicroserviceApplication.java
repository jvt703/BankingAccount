package dev.n1t.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("dev.n1t.model")
public class AccountMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountMicroserviceApplication.class, args);
	}

}
