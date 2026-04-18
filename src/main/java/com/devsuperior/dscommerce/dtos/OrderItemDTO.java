package com.devsuperior.dscommerce.dtos;

import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.Product;

public class OrderItemDTO {
	
	private Long productId;
	private String name;
	private Double price;
	private Integer quantity;
	
	public OrderItemDTO(Long productId, String name, Double price, Integer quantity) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public OrderItemDTO(OrderItem entity) {
		Product product = entity.getProduct();
		
		productId = product.getId();
		name = product.getName();
		price = entity.getPrice();
		quantity = entity.getQuantity();
	}

	public Long getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	public Double getSubTotal() {
		return price *quantity;

	}
}
