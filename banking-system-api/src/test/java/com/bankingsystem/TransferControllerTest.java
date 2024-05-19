package com.bankingsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.model.TransferRequest;
import com.bankingsystem.service.TransactionService;

@RestController
public class TransferControllerTest {

    private final TransactionService transactionService;

    @Autowired
    public TransferControllerTest(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/api/accounts/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest request) {
        // Check if transactionService is null
        if (transactionService == null) {
            // Handle null transactionService
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transaction service is not available");
        }

        // Call the transferFunds method of transactionService
        transactionService.transferFunds(request.getSenderAccountNumber(), request.getRecipientAccountNumber(), request.getAmount());

        // Return response entity
        return ResponseEntity.ok("Funds transferred successfully");
    }
}


