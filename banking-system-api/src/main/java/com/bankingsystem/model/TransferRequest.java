package com.bankingsystem.model;

import java.math.BigDecimal;

public class TransferRequest {
	
	public TransferRequest(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount) {
        this.senderAccountNumber = senderAccountNumber;
        this.recipientAccountNumber = recipientAccountNumber;
        this.amount = amount;
    }

	private String senderAccountNumber;
	
	private String recipientAccountNumber;
	
	private BigDecimal amount;
	
	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}
	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}
	public String getRecipientAccountNumber() {
		return recipientAccountNumber;
	}
	public void setRecipientAccountNumber(String recipientAccountNumber) {
		this.recipientAccountNumber = recipientAccountNumber;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	

	
}
