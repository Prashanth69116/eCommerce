package com.ecommerce.tutorial.ecommercebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.tutorial.ecommercebackend.model.Product;
import com.ecommerce.tutorial.ecommercebackend.repository.ProductDAO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO productDAO;
	
	public List<Product> getProducts() {
		return productDAO.findAll();
	}

}
