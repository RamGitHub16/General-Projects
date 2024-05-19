package com.bankingsystem.exceptionhandling;

public class AccountNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1576422866321072255L;

	public AccountNotFoundException(String message) {
        super(message);
    }
}

