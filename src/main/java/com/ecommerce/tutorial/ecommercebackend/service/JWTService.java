package com.ecommerce.tutorial.ecommercebackend.service;


import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;

import jakarta.annotation.PostConstruct;

@Service
public class JWTService {
	
	@Value("${jwt.algorithm.key}")
	private  String algorithmKey;
	
	@Value("${jwt.issuer}")
	private String issuer;
	
	@Value("${jwt.expiryInSeconds}")
	private int expiryInSeconds;
	
	private Algorithm algorithm;
	
	private static final String USERNAME_KEY = "USERNAME";
	
	private static final String EMAIL_KEY = "EMAIL";
	
	@PostConstruct
	public void postConstruct() {
			algorithm  = Algorithm.HMAC256(algorithmKey);
	}

	public String generateJWT(LocalUser user) {
		return JWT.create()
				.withClaim(USERNAME_KEY, user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
				.withIssuer(issuer)
				.sign(algorithm);
	}
	
	public String generateVarificationJWT(LocalUser user) {
		return JWT.create()
				.withClaim(EMAIL_KEY, user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
				.withIssuer(issuer)
				.sign(algorithm);
	}
	
	
	public String getUsername(String token) {
		return JWT.decode(token).getClaim(USERNAME_KEY).asString();

	}
}
