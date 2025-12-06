package com.restaurant.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restaurant.model.Item;
import com.restaurant.repository.ItemRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ItemRepository repository) {
        return args -> {
            System.out.println("=== DataLoader Started ===");
            System.out.println("Current item count: " + repository.count());
            
            if (repository.count() == 0) {
                System.out.println("Loading sample data...");
                
                Item item1 = new Item();
                item1.setDishname("Spring Rolls");
                item1.setDescription("Crispy vegetable spring rolls served with sweet chili sauce");
                item1.setPrice(120.0);
                item1.setImgUrl("/images/spring-rolls.jpg");
                item1.setCategory("Starters");
                repository.save(item1);
                System.out.println("✓ Saved: " + item1.getDishname());

                Item item2 = new Item();
                item2.setDishname("Chicken Manchurian");
                item2.setDescription("Deep fried chicken in spicy Manchurian sauce");
                item2.setPrice(180.0);
                item2.setImgUrl("/images/chicken-manchurian.jpg");
                item2.setCategory("Starters");
                repository.save(item2);
                System.out.println("✓ Saved: " + item2.getDishname());

                Item item3 = new Item();
                item3.setDishname("Veg Fried Rice");
                item3.setDescription("Aromatic fried rice with mixed vegetables");
                item3.setPrice(150.0);
                item3.setImgUrl("/images/veg-fried-rice.jpg");
                item3.setCategory("Main Course");
                repository.save(item3);
                System.out.println("✓ Saved: " + item3.getDishname());

                Item item4 = new Item();
                item4.setDishname("Hakka Noodles");
                item4.setDescription("Stir-fried noodles with vegetables and sauces");
                item4.setPrice(140.0);
                item4.setImgUrl("/images/hakka-noodles.jpg");
                item4.setCategory("Main Course");
                repository.save(item4);
                System.out.println("✓ Saved: " + item4.getDishname());

                Item item5 = new Item();
                item5.setDishname("Chilli Paneer");
                item5.setDescription("Indo-Chinese style paneer in spicy gravy");
                item5.setPrice(160.0);
                item5.setImgUrl("/images/chilli-paneer.jpg");
                item5.setCategory("Starters");
                repository.save(item5);
                System.out.println("✓ Saved: " + item5.getDishname());

                Item item6 = new Item();
                item6.setDishname("Sweet Corn Soup");
                item6.setDescription("Creamy sweet corn soup with vegetables");
                item6.setPrice(100.0);
                item6.setImgUrl("/images/corn-soup.jpg");
                item6.setCategory("Soups");
                repository.save(item6);
                System.out.println("✓ Saved: " + item6.getDishname());

                Item item7 = new Item();
                item7.setDishname("Szechuan Chicken");
                item7.setDescription("Spicy chicken in Szechuan pepper sauce");
                item7.setPrice(220.0);
                item7.setImgUrl("/images/szechuan-chicken.jpg");
                item7.setCategory("Main Course");
                repository.save(item7);
                System.out.println("✓ Saved: " + item7.getDishname());

                Item item8 = new Item();
                item8.setDishname("Veg Momos");
                item8.setDescription("Steamed dumplings filled with vegetables");
                item8.setPrice(130.0);
                item8.setImgUrl("/images/veg-momos.jpg");
                item8.setCategory("Starters");
                repository.save(item8);
                System.out.println("✓ Saved: " + item8.getDishname());

                System.out.println("✅ Sample menu items loaded successfully!");
                System.out.println("Total items after load: " + repository.count());
            } else {
                System.out.println("ℹ️ Database already contains " + repository.count() + " items. Skipping data load.");
                
                // List existing items
                repository.findAll().forEach(item -> {
                    System.out.println("Existing: " + item.getDishname() + " - ₹" + item.getPrice());
                });
            }
        };
    }
}