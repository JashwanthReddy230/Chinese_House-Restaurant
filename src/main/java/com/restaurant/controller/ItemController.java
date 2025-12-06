package com.restaurant.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.model.Item;
import com.restaurant.model.Register;
import com.restaurant.repository.ItemRepository;
import com.restaurant.service.ItemService;
import com.restaurant.service.UserService;


@Controller
public class ItemController {


	@Autowired
	private ItemService itemService;
	@Autowired
    private ItemRepository itemRepository;
	
	@Autowired
	 public UserService userservice;
	

	private String saveImage(MultipartFile file, String uploadDir) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);
            Files.write(path, file.getBytes());
            return "/images/" + fileName;
        }
        return null;
    }

    @PostMapping("/order")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
           
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile imageFile
           
    ) throws IOException {
     	String uploadDir = new File("uploads/images").getAbsolutePath();
    	File dir = new File(uploadDir);
    	if (!dir.exists()) dir.mkdirs(); // create folder if it doesn't exist

    	String imagePath1 = saveImage(imageFile, uploadDir);
    
    
    Item item = new Item();
    item.setDishname(name);
    item.setDescription(description);
    item.setCategory(category);
    item.setPrice(price);
    item.setImgUrl(imagePath1);
    

    itemService.saveItem(item);
        return "redirect:/dashboard";
    }
    @GetMapping("/index")
    public String getItems(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse("");

        model.addAttribute("role", role); // Pass role to view

        model.addAttribute("item", itemService.getAllItems());
        return "index";
    }
    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Item> items = itemRepository.findAll();
        
        // Debug: Print to console
        System.out.println("=== MENU DEBUG ===");
        System.out.println("Total items found: " + items.size());
        items.forEach(item -> {
            System.out.println("Item: " + item.getDishname() + " | Price: " + item.getPrice() + " | Image: " + item.getImgUrl());
        });
        
        model.addAttribute("items", items);
        return "menu"; // your menu.html template
    }
   
    @GetMapping("/customerTestimonial")
    public String customerTestimonials() {
    	return "customerTestimonial";
    }
    
    @GetMapping("/itemDetails/{id}")
    public String getItemDetails(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id); // Fetch product by ID
        model.addAttribute("item", item);
        return "itemDetails"; // Thymeleaf template name: carDetails.html
    }

    @GetMapping("/edit-item/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        if (item == null) {
            return "redirect:/error"; // or custom 404 page
        }
        model.addAttribute("item", item);
        return "edit_item"; // Same form as add_product but reused
    }
    
    @PostMapping("/update-item")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateItem(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("price") Double price,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
           ) throws IOException {

       Item item = itemService.getItemById(id);
        if (item == null) {
            return "redirect:/error";
        }

        item.setDishname(name);
        item.setDescription(description);
        item.setCategory(category);
        item.setPrice(price);

        String uploadDir = new File("uploads/images").getAbsolutePath();
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        if (imageFile != null && !imageFile.isEmpty())
            item.setImgUrl(saveImage(imageFile, uploadDir));

        itemService.saveItem(item);
        return "redirect:/dashboard";
    }
    @PostMapping("/delete-product")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteItemFromForm(@RequestParam("id") Long id) {
        itemService.deleteItem(id);
        System.out.println("deleting item with id: "+id);
        return "redirect:/dashboard";
    }
    

    
    @PostMapping("/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public String adding(
            @RequestParam("dishname") String dishname,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("price") Double price,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
           ) throws IOException {

       Item item = new Item();
        

        item.setDishname(dishname);
        item.setDescription(description);
        item.setCategory(category);
        item.setPrice(price);

        String uploadDir = new File("uploads/images").getAbsolutePath();
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        if (imageFile != null && !imageFile.isEmpty())
            item.setImgUrl(saveImage(imageFile, uploadDir));

        itemService.saveItem(item);
        System.out.println("success");
        return "redirect:/dashboard";
    }
   

}
