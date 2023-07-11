package com.ecommerce.tutorial.ecommercebackend.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.tutorial.ecommercebackend.model.Product;

public interface ProductDAO extends JpaRepository<Product, Long>{

	List<Product> findAll();
	
	

}
