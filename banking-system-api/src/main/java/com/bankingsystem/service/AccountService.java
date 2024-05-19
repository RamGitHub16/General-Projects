package com.bankingsystem.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import com.bankingsystem.entity.BankAccount;
import com.bankingsystem.entity.Transaction;
import com.bankingsystem.repository.BankAccountRepository;
import com.bankingsystem.repository.TransactionRepository;

@Service
public class AccountService { 
	
	@Autowired
    private BankAccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public BankAccount createAccount(BankAccount account) {
        return accountRepository.save(account);
    }

    public BankAccount getAccountByNumber(String accountNumber) throws NotFoundException {
        Optional<BankAccount> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        return optionalAccount.orElseThrow(NotFoundException::new);
    }

    public BigDecimal getAccountBalance(String accountNumber) throws NotFoundException {
    	 BankAccount account = getAccountByNumber(accountNumber);
         BigDecimal balance = BigDecimal.ZERO;
         if(account != null) {
        	 balance =  account.getBalance();
         }
         return balance;
    }

    public List<Transaction> getAccountTransactions(String accountNumber) throws NotFoundException {
        BankAccount account = getAccountByNumber(accountNumber);
        return transactionRepository.findByAccountOrderByTimestampDesc(account);
    }
}

