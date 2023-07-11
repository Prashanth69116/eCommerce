package com.ecommerce.tutorial.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "web_order_quantity")
public class WebOrdersQuantity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private WebOrder order;
	
	public WebOrder getOrder() {
		return order;
	}
	
	public void setOrder(WebOrder order) {
		this.order=order;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product)
	{
		this.product=product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
