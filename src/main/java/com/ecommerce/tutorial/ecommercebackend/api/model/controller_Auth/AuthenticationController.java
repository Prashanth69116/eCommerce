package com.ecommerce.tutorial.ecommercebackend.api.model.controller_Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.tutorial.ecommercebackend.Exception.EmailFailureException;
import com.ecommerce.tutorial.ecommercebackend.Exception.UserAlreadyExistsException;
import com.ecommerce.tutorial.ecommercebackend.Exception.UserNotVerifiedException;
import com.ecommerce.tutorial.ecommercebackend.api.model.LoginBody;
import com.ecommerce.tutorial.ecommercebackend.api.model.LoginResponse;
import com.ecommerce.tutorial.ecommercebackend.api.model.RegistrationBody;
import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.service.UserService;

import io.micrometer.core.ipc.http.HttpSender.Response;
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
		} catch(UserAlreadyExistsException ex) {
			return new ResponseEntity<>("User with Email "+registrationBody.getEmail()+" and Username "+registrationBody.getUsername()+" are already Exisits",HttpStatus.CONFLICT);
		} catch(EmailFailureException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody)
	{
		String jwt = null;
		try {
			jwt = service.loginUser(loginBody);
		} catch (EmailFailureException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UserNotVerifiedException ex) {
			LoginResponse response = new LoginResponse();
			response.setSucces(false);
			String reason = "USER_NOT_VERIFIED";
			if(ex.isNewEmailSent()) {
				reason += "_EMAIL_RESENT";
			}
			response.setFailureReason(reason);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		if(jwt == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} else {
			LoginResponse response = new LoginResponse();
			response.setJwt(jwt);
			response.setSucces(true);
			return ResponseEntity.ok(response);
		}
	}
	
	@PostMapping("/verify")
	public ResponseEntity verifyEmail(@RequestParam String token) {
		if(service.verifyUser(token)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
			
		
	}
	@GetMapping("/me")
	public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
		return user;
	}
	

}
