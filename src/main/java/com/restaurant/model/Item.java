package com.restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Dishes")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String dishname;
	private String description;
	private Double price;
	private String category;
	public Long getId() {
		return id;
	}
	
	
	public Item(String dishname, String description, Double price, String imgUrl, String category) {
		this.dishname = dishname;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.category = category;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getDishname() {
		return dishname;
	}
	public void setDishname(String dishname) {
		this.dishname = dishname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name="img_url")
	private String imgUrl;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Item(Long id) {
		this.id=id;
	}
	public Item() {
		
	}
}
