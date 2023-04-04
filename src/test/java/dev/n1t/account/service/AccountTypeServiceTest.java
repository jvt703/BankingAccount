package dev.n1t.account.service;

import dev.n1t.account.repository.AccountTypeRepository;
import dev.n1t.model.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountTypeServiceTest {

    @InjectMocks
    private AccountTypeService accountTypeService;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Captor
    ArgumentCaptor<List<AccountType>> argumentCaptor;

    @Test
    void testInitializeAccountTypes() {
        List<AccountType> emptyList = new ArrayList<>();
        when(accountTypeRepository.findAll()).thenReturn(emptyList);

        accountTypeService.initializeAccountTypes();

        verify(accountTypeRepository).saveAll(argumentCaptor.capture());

        List<AccountType> savedAccountTypes = argumentCaptor.getValue();
        assertEquals(4, savedAccountTypes.size());
        assertEquals("Checking", savedAccountTypes.get(0).getAccountTypeName());
        assertEquals("Savings", savedAccountTypes.get(1).getAccountTypeName());
        assertEquals("Credit", savedAccountTypes.get(2).getAccountTypeName());
        assertEquals("Loan", savedAccountTypes.get(3).getAccountTypeName());
    }

    @Test
    void testGetAllAccountTypes() {
        when(accountTypeRepository.findAll()).thenReturn(List.of(
            AccountType.builder()
                .id(1L)
                .accountTypeName("Checking")
                .description("General purpose account for everyday expenses")
                .build(),
            AccountType.builder()
                    .id(2L)
                    .accountTypeName("Savings")
                    .description("Account intended for saving funds")
                    .build(),
            AccountType.builder()
                    .id(3L)
                    .accountTypeName("Credit")
                    .description("Account tied to a credit card with a rewards points system")
                    .build(),
            AccountType.builder()
                    .id(4L)
                    .accountTypeName("Loan")
                    .description("Account whose balance reflects the owed amount on a loan")
                    .build()));

        assertEquals(4, accountTypeService.getAllAccountTypes().size());
        assertEquals("Checking", accountTypeService.getAllAccountTypes().get(0).getAccountTypeName());
        assertEquals("Savings", accountTypeService.getAllAccountTypes().get(1).getAccountTypeName());
        assertEquals("Credit", accountTypeService.getAllAccountTypes().get(2).getAccountTypeName());
        assertEquals("Loan", accountTypeService.getAllAccountTypes().get(3).getAccountTypeName());

    }
}