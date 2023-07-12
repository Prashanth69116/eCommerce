package com.ecommerce.tutorial.ecommercebackend.repository;


import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.model.VerificationToken;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long>{

	Optional<VerificationToken> findByToken(String token);

	void deleteByUser(LocalUser user);

}
