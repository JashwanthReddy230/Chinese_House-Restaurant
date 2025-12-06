package com.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Order;
import com.restaurant.model.Register;
import com.restaurant.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository OrderRepository;
	
	public void saveOrder(Order order) {
		OrderRepository.save(order);
	}
	public List<Order> getOrdersByRegister(Register register) {
		return OrderRepository.findByRegister(register);
	}
	public Order getOrderById(long id) {
		return OrderRepository.findById(id).orElse(null);
	}
	public List<Order> getAllOrders(){
		return OrderRepository.findAll() ;
	}
}


