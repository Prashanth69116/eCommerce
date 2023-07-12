package com.ecommerce.tutorial.ecommercebackend.service;



import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.tutorial.ecommercebackend.Exception.EmailFailureException;
import com.ecommerce.tutorial.ecommercebackend.Exception.UserAlreadyExistsException;
import com.ecommerce.tutorial.ecommercebackend.Exception.UserNotVerifiedException;
import com.ecommerce.tutorial.ecommercebackend.api.model.LoginBody;
import com.ecommerce.tutorial.ecommercebackend.api.model.RegistrationBody;
import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.model.VerificationToken;
import com.ecommerce.tutorial.ecommercebackend.repository.LocalUserDAO;
import com.ecommerce.tutorial.ecommercebackend.repository.VerificationTokenDAO;

import jakarta.transaction.Transactional;






@Service
public class UserService {
	
	@Autowired 
	private LocalUserDAO repository;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired 
	private VerificationTokenDAO verificationTokenDAO;
	
	public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException,  EmailFailureException{
	
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
			VerificationToken verificationToken = createVerificationToken(user);
			emailService.sendVerificationEmail(verificationToken);
			//verificationTokenDAO.save(verificationToken);
			return repository.save(user);
		}
	
	private VerificationToken createVerificationToken(LocalUser user) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(jwtService.generateVarificationJWT(user));
		verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		verificationToken.setUser(user);
		user.getVerificationToken().add(verificationToken);
		return verificationToken;
	}
	
	public String loginUser(LoginBody loginBody) throws EmailFailureException, UserNotVerifiedException {
		
		Optional<LocalUser> opUser = repository.findByUsernameIgnoreCase(loginBody.getUsername());
		if(opUser.isPresent()) {
			LocalUser user = opUser.get();
			if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
				if(user.isEmailVerified()) {
					return jwtService.generateJWT(user);
				} else  {
					List<VerificationToken> verificationToken = user.getVerificationToken();
					boolean resend= verificationToken.size() == 0 || verificationToken.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis()-(60 * 60 * 1000)));
				if(resend) {
					VerificationToken verificationToken1 = createVerificationToken(user);
					verificationTokenDAO.save(verificationToken1);
					emailService.sendVerificationEmail(verificationToken1);
					
				}
				throw new UserNotVerifiedException(resend);
				}
			}
		}
		return null;
	}
	@Transactional
	public boolean verifyUser(String token) {
		Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
		if(opToken.isPresent()) {
			VerificationToken verificationToken = opToken.get();
			LocalUser user = verificationToken.getUser();
			if(!user.isEmailVerified()) {
				user.setEmailVerified(true);
				repository.save(user);
				verificationTokenDAO.deleteByUser(user);
				return true;
			}
		}
		return false;
		
	}
		
}
