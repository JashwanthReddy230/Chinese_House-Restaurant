package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Cartitems;
@Repository
public interface CartRepository extends JpaRepository<Cartitems, Long>{
	List<Cartitems> findByUsername(String username);
	Cartitems findByUsernameAndItemId(String username,Long itemId);
}
