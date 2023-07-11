package com.ecommerce.tutorial.ecommercebackend.api.model.controller_Auth.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.tutorial.ecommercebackend.model.Product;
import com.ecommerce.tutorial.ecommercebackend.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<Product> getProducts() {
		return productService.getProducts();
	}

}
