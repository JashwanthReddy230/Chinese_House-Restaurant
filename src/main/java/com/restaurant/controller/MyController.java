package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

		
//		@GetMapping("/index")
//		public String index() {
//			
//			return "index";
//		}
		
		@GetMapping("/about")
		public String About() {
			
			return "about";
		}
		
//		@GetMapping("/menu")
//		public String Menu() {
//			
//			return"menu";
//		}
		
//		@GetMapping("/login")
//		public String Login() {
//			return "login";
//		}
		
//		@GetMapping("/cart")
//		public String Cart() {
//			return"cart";
//		}
		@GetMapping("/register")
		public String Signin() {
			return"signin";
		}
		@GetMapping("/forgotpassword")
		public String Forgotpassword() {
			return"forgotpassword";
		}
		
		
	}  


