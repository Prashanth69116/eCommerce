package com.ecommerce.tutorial.ecommercebackend.api.model;

public class LoginResponse {
	private String jwt;

	private boolean succes;
	
	private String failureReason;
	
	
	public boolean getSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	

}
