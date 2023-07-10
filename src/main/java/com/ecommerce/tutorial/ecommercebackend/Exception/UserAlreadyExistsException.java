package com.ecommerce.tutorial.ecommercebackend.Exception;

public class UserAlreadyExistsException extends RuntimeException	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException() {
		// TODO Auto-generated constructor stub
	}

}
