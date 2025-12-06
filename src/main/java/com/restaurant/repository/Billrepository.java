package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Bill;

@Repository
public interface Billrepository extends JpaRepository<Bill, Long> {
	Bill findByOid(long oid);

}
