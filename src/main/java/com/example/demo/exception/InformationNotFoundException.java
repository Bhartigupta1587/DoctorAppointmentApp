package com.example.demo.exception;

@SuppressWarnings("serial")
public class InformationNotFoundException extends RuntimeException {
	
	public InformationNotFoundException(String message) {
		super(message);
	}

}