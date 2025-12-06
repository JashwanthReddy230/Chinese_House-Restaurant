package com.restaurant.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Register;
import com.restaurant.repository.RegisterRepository;
@Service
public class UserServiceImp  implements UserService{
	@Autowired
	private RegisterRepository ur;
	@Override
	public void saveUser(Register users) {
		// TODO Auto-generated method stub
		ur.save(users);
	}

	@Override
	public List<Register> getAllUsers() {
		// TODO Auto-generated method stub
		return ur.findAll();
	}

	
}
