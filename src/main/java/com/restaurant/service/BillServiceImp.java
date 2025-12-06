package com.restaurant.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.restaurant.repository.Billrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Bill;

@Service
public class BillServiceImp implements BillService{

    private final Billrepository billrepository;

    BillServiceImp(Billrepository billrepository) {
        this.billrepository = billrepository;
    }

	@Autowired
	public String generateInvoiceNumber() {
		DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return "INV-"+ LocalDateTime.now().format(formatter);
	}

	@Override
	public void saveBill(Bill bill) {
		billrepository.save(bill);
		
	}

}
