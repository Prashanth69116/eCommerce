package com.ecommerce.tutorial.ecommercebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;


@Repository
public interface LocalUserDAO extends JpaRepository<LocalUser, Long> {
	

	Optional<LocalUser> findByUsernameIgnoreCase(String username);
	Optional<LocalUser> findByEmailIgnoreCase(String email);
			
	
	

}
