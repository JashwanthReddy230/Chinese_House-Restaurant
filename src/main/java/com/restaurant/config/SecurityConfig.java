package com.restaurant.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.restaurant.service.CustomRegisterDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomRegisterDetailsService userDetailsService;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				// Public access - no authentication required
				.requestMatchers(
					"/register",
					"/login",
					"/forgot-password",
					"/index",
					"/home",
					"/about",
					"/menu",
					"/reservation",          // Allow users to submit reservations
					"/css/**",
					"/js/**",
					"/images/**",
					"/logo-img/**",
					"/api/items/**",
					"/api/debug/**"
				).permitAll()
				
				// Admin only access
				.requestMatchers(
					"/admin/**",             // All admin reservation endpoints
					"/dashboard",
					"/add-product",
					"/edit-item/**",
					"/delete-product",
					"/order/*/accept",
					"/orders/*/reject",
					"/orders/*/completed"
				).hasRole("ADMIN")
				
				// Authenticated user access
				.requestMatchers(
					"/book/**", 
					"/my-orders",
					"/cart",
					"/add-to-cart",
					"/order/**"
				).authenticated()
				
				// All other requests require authentication
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/login")
				.successHandler(successHandler())
				.failureUrl("/login?error=true")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.permitAll()
			)
			.csrf().disable(); // Disable CSRF for testing (enable in production if needed)
		
		return http.build();
	}
	
	@Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    response.sendRedirect("/dashboard");
                } else {
                    response.sendRedirect("/home");
                }
            }
        };
    }
}