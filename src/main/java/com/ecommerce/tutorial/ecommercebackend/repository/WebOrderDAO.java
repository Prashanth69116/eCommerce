package com.ecommerce.tutorial.ecommercebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.model.WebOrder;

public interface WebOrderDAO  extends JpaRepository<WebOrder, Long>{
	List<WebOrder> findByUser(LocalUser user);

}
