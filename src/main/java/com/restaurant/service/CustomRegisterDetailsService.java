package com.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.restaurant.model.*;

import com.restaurant.repository.RegisterRepository;


//@Service
//public class CustomRegisterDetailsService implements UserDetailsService {
//
//	@Autowired
//	private RegisterRepository registerRepository;
//	
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		Register register=registerRepository.findByUsername(username)
//				.orElseThrow( ()-> new UsernameNotFoundException("user not available"));
//		
//		return new org.springframework.security.core.userdetails.User(
//				
//				register.getUsername(),
//				register.getPassword(),
//				
//				List.of(new SimpleGrantedAuthority(register.getRole()))
//				
//				);
//			
//	
//	}
//	
//	 public Register findByUsername(String username) {
//	        return registerRepository.findByUsername(username)
//	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//	    }
//	    
//	    public Register getUserById(Long id) {
//	        return registerRepository.findById(id).orElse(null);
//	    }
//
//}




@Service
public class CustomRegisterDetailsService implements UserDetailsService {

	@Autowired
	private RegisterRepository registerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Register user = registerRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            
            List.of(new SimpleGrantedAuthority(user.getRole()))
        );
        
    }
    
    public Register findByUsername(String username) {
        return registerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    
    public Register getUserById(Long id) {
        return registerRepository.findById(id).orElse(null);
    }

    
    
}

