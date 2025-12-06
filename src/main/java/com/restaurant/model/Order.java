package com.restaurant.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Item item;
	
	@ManyToOne(cascade = CascadeType.PERSIST) // or CascadeType.ALL if appropriate
	private Register register;
	
	private LocalDateTime orderdate;
	
	private String address;
	
	private double price;
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private double totalprice;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public LocalDateTime getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(LocalDateTime orderdate) {
		this.orderdate = orderdate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public long Quantity;

	public long getQuantity() {
		return Quantity;
	}

	public void setQuantity(long quantity) {
		Quantity = quantity;
	}
	
	
	
	
}
