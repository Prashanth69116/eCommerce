package com.ecommerce.tutorial.ecommercebackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.ecommerce.tutorial.ecommercebackend.Exception.UserAlreadyExistsException;
import com.ecommerce.tutorial.ecommercebackend.api.model.LoginBody;
import com.ecommerce.tutorial.ecommercebackend.api.model.RegistrationBody;
import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.repository.LocalUserDAO;






@Service
public class UserService {
	
	@Autowired 
	private LocalUserDAO repository;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private JWTService jwtService;
	
	public LocalUser registerUser(RegistrationBody registrationBody) throws Exception {
	
		if(repository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
				|| repository.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent())
			{
				throw new UserAlreadyExistsException("User with Email "+registrationBody.getEmail()+" and Username "+registrationBody.getUsername()+" are already Exisits");
			}
			
			LocalUser user=new LocalUser();
			
			user.setUsername(registrationBody.getUsername());
			user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()))	;
			user.setEmail(registrationBody.getEmail());
			user.setFirstName(registrationBody.getFirstname());
			user.setLastName(registrationBody.getLastname());
			return repository.save(user);
		
			
		}
	
	public String loginUser(LoginBody loginBody) {
		
		Optional<LocalUser> opUser = repository.findByUsernameIgnoreCase(loginBody.getUsername());
		if(opUser.isPresent()) {
			LocalUser user = opUser.get();
			if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
				return jwtService.generateJWT(user);
			}
		}
		return null;
		
	}
		
}
