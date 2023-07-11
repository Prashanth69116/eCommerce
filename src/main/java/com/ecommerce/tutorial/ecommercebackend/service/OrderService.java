package com.ecommerce.tutorial.ecommercebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.model.WebOrder;
import com.ecommerce.tutorial.ecommercebackend.repository.WebOrderDAO;

@Service
public class OrderService {
	
	@Autowired
	private WebOrderDAO webOrderDAO;
	


	public List<WebOrder> getOrders(LocalUser user) {
		return webOrderDAO.findByUser(user);
	}
	

}
