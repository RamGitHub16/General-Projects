package com.bankingsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.constant.ApiConstant;
import com.bankingsystem.model.TransferRequest;
import com.bankingsystem.service.TransactionService;

@RestController
@RequestMapping(ApiConstant.APIROOT)
public class TransferRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransferRestController.class);

    @Autowired
    private TransactionService transactionService;


    @PostMapping(value = ApiConstant.CREATE_TRANSACTION)
    public  ResponseEntity<String> transferFunds(@RequestBody TransferRequest transferRequest) {
    	logger.info("Geting inside the transfer funds method");
        transactionService.transferFunds(transferRequest.getSenderAccountNumber(),
                                         transferRequest.getRecipientAccountNumber(),
                                         transferRequest.getAmount());
        logger.info("Fund Trasferred Successfully");
		return ResponseEntity.ok("Transfer successful");
    }
}

