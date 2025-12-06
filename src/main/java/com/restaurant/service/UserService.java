package com.restaurant.service;

import java.util.List;

import com.restaurant.model.Register;

public interface UserService {
	void saveUser(Register user);
	List<Register> getAllUsers();
}
