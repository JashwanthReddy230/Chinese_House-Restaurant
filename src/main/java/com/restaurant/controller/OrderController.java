package com.restaurant.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restaurant.model.Item;
import com.restaurant.model.Order;
import com.restaurant.model.Register;
import com.restaurant.service.CustomRegisterDetailsService;
import com.restaurant.service.ItemService;
import com.restaurant.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomRegisterDetailsService registerService;

    @PostMapping("/order/{itemId}")
    public String orderItem(@PathVariable Long itemId,
                            @RequestParam("orderDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                            @RequestParam("address") String addressLocation,
                            @RequestParam("items") Long quantity,
                            Principal principal) {

        // Get the ordered item and logged-in user
        Item item = itemService.getItemById(itemId);
        Register register = registerService.findByUsername(principal.getName());

        if (item == null || register == null) {
            return "redirect:/error"; // Handle errors gracefully
        }

        // Create order
        Order order = new Order();
        order.setItem(item);
        order.setRegister(register);
        order.setOrderdate(date);
        order.setAddress(addressLocation);
        order.setQuantity(quantity);
        order.setPrice(item.getPrice());

        // Calculate total price
        double totalPrice = item.getPrice() * quantity;
        order.setTotalprice(totalPrice);

        order.setStatus("PENDING");
        orderService.saveOrder(order);

        return "redirect:/my-orders";
    }

    @GetMapping("/my-orders")
    public String viewMyOrders(Model model, Principal principal) {
        Register register = registerService.findByUsername(principal.getName());
        model.addAttribute("orders", orderService.getOrdersByRegister(register));
        model.addAttribute("register", register);
        return "my-orders";
    }

    @GetMapping("/item-details/{id}")
    public String viewProductDetails(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        if (item == null) {
            return "redirect:/error"; // Handle item not found
        }
        model.addAttribute("item", item);
        model.addAttribute("today", LocalDate.now());
        return "item-details";
    }
}
