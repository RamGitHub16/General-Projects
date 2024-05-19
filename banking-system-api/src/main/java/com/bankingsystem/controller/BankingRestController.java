package com.bankingsystem.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.constant.ApiConstant;
import com.bankingsystem.entity.BankAccount;
import com.bankingsystem.entity.Transaction;
import com.bankingsystem.service.AccountService;




@RestController
@RequestMapping(ApiConstant.APIROOT)
public class BankingRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BankingRestController.class);
	
	@Autowired
    private AccountService accountService;

    @PostMapping(value = ApiConstant.CREATE_ACCOUNT)
    public ResponseEntity<BankAccount> createAccount(@RequestBody BankAccount account) {
    	logger.info("Geting inside the  account creation method");
        BankAccount createdAccount = accountService.createAccount(account);
        logger.info("Account created succesfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }


    @GetMapping(value = ApiConstant.GET_ACCOUNT_DETAILS)
    public ResponseEntity<BankAccount> getAccountByNumber(@PathVariable String accountNumber) throws NotFoundException {
    	logger.info("Geting inside the  account details method");
        BankAccount account = accountService.getAccountByNumber(accountNumber);
        logger.info("Fetched account details successfully");
        return ResponseEntity.ok(account);
    }

    @GetMapping(value = ApiConstant.GET_ACCOUNT_BANLANCE)
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable String accountNumber) throws NotFoundException {
    	logger.info("Geting inside the  account balance method");
        BigDecimal balance = accountService.getAccountBalance(accountNumber);
        logger.info("Fetched account balance successfully");
        return ResponseEntity.ok(balance);
    }

    @GetMapping(value = ApiConstant.GET_TRANSACTION_DETAILS)
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable String accountNumber) throws NotFoundException {
    	logger.info("Geting inside the  account transcation method");
        List<Transaction> transactions = accountService.getAccountTransactions(accountNumber);
        logger.info("Fetched account transaction details successfully");
        return ResponseEntity.ok(transactions);
    }
	
		
}
