package com.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Register;
import java.util.List;


@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
	Optional<Register> findByUsername(String username);
	Optional<Register> findFirstByUsername(String Username);
	
	Optional<Register> findByUsernameAndEmail(String username, String email);
	Optional<Register> findById(Long id);
	
	boolean existsByUsername(String username);
    List<Register> findByRole(String role);
}
