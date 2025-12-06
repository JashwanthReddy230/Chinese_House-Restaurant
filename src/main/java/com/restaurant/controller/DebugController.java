package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Item;
import com.restaurant.repository.ItemRepository;

@RestController
@RequestMapping("/api")
public class DebugController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    @GetMapping("/items/count")
    public String getItemCount() {
        return "Total items in database: " + itemRepository.count();
    }
}