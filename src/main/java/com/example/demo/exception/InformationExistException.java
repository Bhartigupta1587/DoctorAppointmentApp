package com.example.demo.exception;

public class InformationExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InformationExistException(String message) {
		super(message);
	}
}
