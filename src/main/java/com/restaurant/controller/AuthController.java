package com.restaurant.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.restaurant.model.Register;
import com.restaurant.repository.RegisterRepository;


@Controller
public class AuthController{

	@Autowired
	private RegisterRepository registerRepository;
	
	@Autowired
	private PasswordEncoder encoder;

//    AuthController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
	
	
	@GetMapping("/user-dashboard")
	public String gotoDashboard() {
		return "dashboard";
	}
	
	
	
	
	@PostMapping("/register")
	public String registerUser (@RequestParam String firstname, @RequestParam String lastname,@RequestParam String email, @RequestParam long phonenumber, @RequestParam String username, @RequestParam String password) {
		Register register=new Register();
		register.setFirstname(firstname);
		register.setLastname(lastname);
		register.setEmail(email);
		register.setPhonenumber(phonenumber);
		register.setUsername(username);
		register.setPassword(encoder.encode(password));
//		register.setPassword(password);
		register.setRole("ROLE_USER");
		registerRepository.save(register);
//	@PostMapping("/register")
//	public String registerUser(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam Long phonenumber,  ) {
//		registerRepository.save(register);
		System.out.println("email is: "+register.getEmail());
		
		return "redirect:/login";

	}
	@GetMapping("/home")
	public String mm() {
		return "index";
	}
	
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	    @GetMapping("/forgot-password")
	    public String showForgotPasswordForm() {
	        return "forgot_password";
	    }
	    
	    
	    @PostMapping("/forgot-password")
	    public String verifyRegister(@RequestParam String username,
	                             @RequestParam String email,
	                             Model model) {
	    	Optional<Register> registerOptional= registerRepository.findByUsernameAndEmail(username, email);
	    	if(registerOptional.isPresent()) {
	    		model.addAttribute("username",username);
	    		return "reset_password";
	    	}else {
	    		 model.addAttribute("error", "User not found. Please check your details.");
	     	    return "forgot_password";
	    		
	    	}			
	    }
	    
	    
	    @PostMapping("/reset-password")
	    public String resetPassword(@RequestParam String username,
	                                @RequestParam String password,
	                                @RequestParam String confirmPassword,
	                                Model model) {

	        if (!password.equals(confirmPassword)) {
	        model.addAttribute("error","Passwords do  not match.");
	        model.addAttribute("username", username);
            return "reset_password";
	        }
	        Optional<Register> registerOptional = registerRepository.findByUsername(username);
	        model.addAttribute("status","deactive");
	        if (registerOptional.isPresent()) {
	            Register register = registerOptional.get();
	            register.setPassword(encoder.encode(password));
	            register.setPassword(password);
	            registerRepository.save(register);
	            model.addAttribute("success", "Password updated successfully.");
	            model.addAttribute("status","active");
	        } else {
	            model.addAttribute("error", "User not found.");
	            model.addAttribute("status","deactive");
	        }
	        
	        
	        
			return confirmPassword;
}
	    @GetMapping("/edit-profile/{id}")
	    public String editUserForm(@PathVariable Long id, Model model) {
	        Optional<Register> register = registerRepository.findById(id);
	        model.addAttribute("user", register);
	        return "edit_userInfo";
	    }

	    @PostMapping("/update-profile")
	    		public String updateProduct(@RequestParam long id,@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email, @RequestParam long phonenumber,@RequestParam String username, @RequestParam String password) {
	    	Register register = new Register();
	    	register.setFirstname(firstName);
	    	register.setLastname(lastName);
	    	register.setEmail(email);
	    	register.setPhonenumber(phonenumber);
	    	register.setUsername(username);
	    	register.setPassword(encoder.encode(password));
	    	register.setPassword(password);
	    	register.setRole("ROLE_USER");
	    	registerRepository.save(register);
	    	return "redirect:/my-orders";

}
	    
}