package com.bankingsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingsystem.entity.BankAccount;
import com.bankingsystem.entity.Transaction;
import com.bankingsystem.exceptionhandling.AccountNotFoundException;
import com.bankingsystem.exceptionhandling.InsufficientFundsException;
import com.bankingsystem.repository.BankAccountRepository;
import com.bankingsystem.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private BankAccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void transferFunds(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount) {
        BankAccount senderAccount = accountRepository.findByAccountNumber(senderAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        BankAccount recipientAccount = accountRepository.findByAccountNumber(recipientAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Recipient account not found"));

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in sender account");
        }

        // Update sender's balance
        BigDecimal senderNewBalance = senderAccount.getBalance().subtract(amount);
        senderAccount.setBalance(senderNewBalance);
        accountRepository.save(senderAccount);

        // Update recipient's balance
        BigDecimal recipientNewBalance = recipientAccount.getBalance().add(amount);
        recipientAccount.setBalance(recipientNewBalance);
        accountRepository.save(recipientAccount);

     // Record transaction for sender
        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccount(senderAccount);
        senderTransaction.setTimestamp(LocalDateTime.now());
        senderTransaction.setAmount(amount.negate()); // Negative amount for debit
        senderTransaction.setDescription("Transferred to " + recipientAccountNumber);
        transactionRepository.save(senderTransaction);

        // Record transaction for recipient
        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setAccount(recipientAccount);
        recipientTransaction.setTimestamp(LocalDateTime.now());
        recipientTransaction.setAmount(amount); // Positive amount for credit
        recipientTransaction.setDescription("Transferred from " + senderAccountNumber);
        transactionRepository.save(recipientTransaction);
    }
}
