package com.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Item;
import com.restaurant.repository.ItemRepository;
import com.restaurant.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemServiceImp implements ItemService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Override
	public void saveItem(Item item) {
		// TODO Auto-generated method stub
		itemRepository.save(item);
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		return itemRepository.findAll();
	}

	@Override
	public Item getItemById(Long id) {
		// TODO Auto-generated method stub
		return itemRepository.findById(id)
				.orElseThrow(()->new RuntimeException("item not found with id:"+id));
	}

	@Override
	public void updateItem(Long id, Item updatedItem) {
		Item existing = itemRepository.findById(id).orElseThrow();
		existing.setDishname(updatedItem.getDishname());
		existing.setDescription(updatedItem.getDescription());
		existing.setCategory(updatedItem.getCategory());
		existing.setPrice(updatedItem.getPrice());
		existing.setImgUrl(updatedItem.getImgUrl());
		itemRepository.save(existing);
		
	}

	@Override
	@Transactional
	public void deleteItem(Long id) {
		orderRepository.deleteByItemId(id);
		itemRepository.deleteById(id);

		// TODO Auto-generated method stub
		
	}

}
