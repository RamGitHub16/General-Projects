package com.bankingsystem.exceptionhandling;

public class InsufficientFundsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6290881329718400183L;

	public InsufficientFundsException(String message) {
        super(message);
    } 
}
