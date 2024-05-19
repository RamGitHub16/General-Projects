package com.bankingsystem.constant;

public class ApiConstant {
	
	private ApiConstant() {
	}

	public static final String APIROOT = "/api/v1";

	public static final String CREATE_ACCOUNT = "/accounts";
	public static final String GET_ACCOUNT_DETAILS = "/accounts/{accountNumber}";
	public static final String GET_ACCOUNT_BANLANCE = "/accounts/{accountNumber}/balance";
	public static final String CREATE_TRANSACTION = "/accounts/transfer";
	public static final String GET_TRANSACTION_DETAILS = "/accounts/{accountNumber}/transactions";
	
	
}
