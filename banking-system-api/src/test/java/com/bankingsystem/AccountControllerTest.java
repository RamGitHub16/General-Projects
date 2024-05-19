package com.bankingsystem;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;

import com.bankingsystem.controller.BankingRestController;
import com.bankingsystem.entity.BankAccount;
import com.bankingsystem.service.AccountService;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

	@Mock
	private AccountService accountService; // Mocked accountService

	@InjectMocks
	private BankingRestController accountController;

	@Test
    public void testCreateAccount() {
        // Mock behavior of accountService.createAccount()
        when(accountService.createAccount(any(BankAccount.class))).thenReturn(new BankAccount());

        // Create a BankAccount object
        BankAccount account = new BankAccount();
        account.setAccountNumber("1234567890");
        account.setAccountHolderName("John Doe");
        account.setAccountType("Savings");
        account.setBalance(BigDecimal.valueOf(1000));

        // Call the controller method
        ResponseEntity<BankAccount> responseEntity = accountController.createAccount(account);

        // Perform assertions on the response entity
        // For example:
        // assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // assertEquals("1234567890", responseEntity.getBody().getAccountNumber());
    }

	@Test
    public void testGetAccountByNumber() throws NotFoundException {
        // Mock behavior of accountService.getAccountByNumber()
        when(accountService.getAccountByNumber(anyString())).thenReturn(new BankAccount());

        // Call the controller method
        ResponseEntity<BankAccount> responseEntity = accountController.getAccountByNumber("1234567890");

        // Perform assertions on the response entity
        // ...
    }
}
