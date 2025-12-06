package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Order;
import com.restaurant.model.Register;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	//List<Order> findByRegister(Register register);
	
	List<Order> findByRegister(Register register);
	@Modifying
	@Query("DELETE FROM Order o WHERE o.item.id = :itemId")
	void deleteByItemId(@Param("itemId") Long itemId);


}
