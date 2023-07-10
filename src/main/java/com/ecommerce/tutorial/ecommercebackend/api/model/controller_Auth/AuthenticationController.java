package com.ecommerce.tutorial.ecommercebackend.api.model.controller_Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.tutorial.ecommercebackend.Exception.UserAlreadyExistsException;
import com.ecommerce.tutorial.ecommercebackend.api.model.LoginBody;
import com.ecommerce.tutorial.ecommercebackend.api.model.LoginResponse;
import com.ecommerce.tutorial.ecommercebackend.api.model.RegistrationBody;
import com.ecommerce.tutorial.ecommercebackend.service.UserService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	 
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationBody registrationBody ) throws Exception{
		try {
			 service.registerUser(registrationBody);
			 return new ResponseEntity<>("User details are added successfully. ", HttpStatus.OK);
		}
		catch(UserAlreadyExistsException ex)
		{
			return new ResponseEntity<>("User with Email "+registrationBody.getEmail()+" and Username "+registrationBody.getUsername()+" are already Exisits",HttpStatus.CONFLICT);
		}	
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody)
	{
		String jwt = service.loginUser(loginBody);
		if(jwt == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} else {
			LoginResponse response = new LoginResponse();
			response.setJwt(jwt);
			return ResponseEntity.ok(response);
		}
	}
	

}
