package com.restaurant.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="oid")
	private long oid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	@Column(name="invoice")
	private String invoiceNumber;
	@Column(name="name")
	private String customerName;
	@Column(name="item")
	private String itemName;
	
	 @Column(name="peritem")
	    private double ratePerItem;
	    
	    @Column(name="total")
	    private double totalAmount;
	
	public double getRatePerItem() {
			return ratePerItem;
		}
		public void setRatePerItem(double ratePerItem) {
			this.ratePerItem = ratePerItem;
		}
		public double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}
	private LocalDateTime orderDate;
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime localDateTime) {
		this.orderDate = localDateTime;
	}
	

}
