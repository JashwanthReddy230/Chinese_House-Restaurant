package com.restaurant.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Cartitems;
import com.restaurant.model.Item;
import com.restaurant.model.Order;
import com.restaurant.model.Register;
import com.restaurant.repository.CartRepository;
import com.restaurant.repository.OrderRepository;
import com.restaurant.repository.RegisterRepository;
import com.restaurant.service.CustomRegisterDetailsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    private final AdminController adminController;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomRegisterDetailsService registerService;

    CartController(AdminController adminController) {
        this.adminController = adminController;
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long itemId,
                            @RequestParam String ItemName,
                            @RequestParam double price,
                            Authentication auth) {
        String username = auth.getName();

        Cartitems existingItem = cartRepository.findByUsernameAndItemId(username, itemId);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartRepository.save(existingItem);
        } else {
            Cartitems item = new Cartitems();
            item.setUsername(username);
            item.setItemId(itemId);
            item.setItemName(ItemName);
            item.setPrice(price);
            item.setQuantity(1);
            cartRepository.save(item);
        }

        return "redirect:/cart";
    }
    @PostMapping("/update-cart")
    public String updateCart(@RequestParam("id") Long itemId,
                             @RequestParam("quantity") int quantity,
                             Authentication auth) {

        Optional<Cartitems> optionalItem = cartRepository.findById(itemId);

        if (optionalItem.isPresent()) {
            Cartitems item = optionalItem.get();

            // Optional: Check if the item belongs to the logged-in user
            if (item.getUsername().equals(auth.getName())) {
                item.setQuantity(quantity);
                cartRepository.save(item); // Save updated quantity to DB
            }
        }

        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String viewCart(Model model, Authentication auth) {
        List<Cartitems> cartItems = cartRepository.findByUsername(auth.getName());
        double total = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("cartitems", cartItems);
        model.addAttribute("totalPrice", total);
        model.addAttribute("now", LocalDateTime.now()); 
        return "cart";
    }
//    @GetMapping("/cart")
//    public String showCart(Model model) {
//        List<CartItem> cartItems = cartService.getCartItemsForCurrentUser(); // or however you're loading it
//
//        double total = cartItems.stream()
//            .mapToDouble(item -> item.getPrice() * item.getQuantity())
//            .sum();
//
//        model.addAttribute("cartitems", cartItems);
//        model.addAttribute("totalPrice", total);
//
//        return "cart";
//    }


    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam Long id) {
        cartRepository.deleteById(id);
        return "redirect:/cart";
    }

//
    @PostMapping("/cart/order")
    public String placeOrder(@RequestParam("address") String address,
                             @RequestParam("orderDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderDate,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {

        
        
        Register register = registerService.findByUsername(principal.getName());
        String username = register.getUsername();
        List<Cartitems> cartItems = cartRepository.findByUsername(username);
        if (cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty.");
            return "redirect:/cart";
        }

        for (Cartitems item : cartItems) {
            Order order = new Order();
            
            // Set required fields
            order.setItem(new Item(item.getItemId())); // assuming Item has a constructor with id
            order.setRegister(register); // same assumption
            order.setPrice(item.getPrice());
            order.setQuantity(item.getQuantity());
            order.setTotalprice(item.getPrice() * item.getQuantity());
            order.setAddress(address);
            order.setOrderdate(orderDate);
            order.setStatus("Pending");

            orderRepository.save(order);
        }

        // Clear the cart
        cartRepository.deleteAll(cartItems);

        redirectAttributes.addFlashAttribute("success", "Your order has been placed successfully!");
        return "redirect:/cart";
    }

}
