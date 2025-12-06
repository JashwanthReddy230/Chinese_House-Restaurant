package com.restaurant.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.restaurant.model.Register;
import com.restaurant.repository.RegisterRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
    	if (!registerRepository.existsByUsername("admin@123")) {
    	    Register admin = new Register();
    	    admin.setUsername("admin@123");
    	    admin.setPassword(passwordEncoder.encode("admin@123"));
    	    admin.setRole("ROLE_ADMIN");
    	    admin.setEnabled(true); // ✅ Fix added

    	    registerRepository.save(admin);
    	    System.out.println("✅ Admin user created successfully.");
    	}
    }
}
