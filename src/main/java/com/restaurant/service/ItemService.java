package com.restaurant.service;

import java.util.List;

import com.restaurant.model.Item;

public interface ItemService {
	void saveItem(Item item);
	List<Item> getAllItems();
	Item getItemById(Long id);
	void updateItem(Long id, Item updateItem);
	void deleteItem(Long id);
	

}
