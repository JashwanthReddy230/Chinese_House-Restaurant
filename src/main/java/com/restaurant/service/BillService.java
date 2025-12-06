package com.restaurant.service;

import com.restaurant.model.Bill;

public interface BillService {
	public String generateInvoiceNumber();
	public void saveBill(Bill bill);

}
