package com.ecommerce.tutorial.ecommercebackend.api.model.controller_Auth.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.model.WebOrder;
import com.ecommerce.tutorial.ecommercebackend.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user) {
		return orderService.getOrders(user);
	}

}
