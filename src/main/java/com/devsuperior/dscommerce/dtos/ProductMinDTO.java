package com.devsuperior.dscommerce.dtos;

import com.devsuperior.dscommerce.entities.Product;

public class ProductMinDTO {
	
	private Long id;
	private String name;
	private Double price;
	private String imgUrl;
	
	public ProductMinDTO(Product product) {
		id = product.getId();
		name = product.getName();
		price = product.getPrice();
		imgUrl = product.getImgUrl();
	}
	
	public ProductMinDTO() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	
}
