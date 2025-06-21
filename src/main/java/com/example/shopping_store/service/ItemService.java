package com.example.shopping_store.service;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public String saveItem(Item item) {
        if (item.getPhoto().trim().isEmpty() || item.getTitle().trim().isEmpty() || item.getPrice() == 0 || item.getStock() == 0) {
            return "Fields cannot be empty";
        }

        if (itemRepository.getItemById(item.getId()) == null) {


            String result = itemRepository.saveItem(item);
            if (result.contains("successfully")) {
                return result;
            }
            return result;
        }
        return "Item already exists";
    }
    public String updateItem(Item item) {
        if (item.getPhoto().trim().isEmpty() || item.getTitle().trim().isEmpty() || item.getPrice() == 0 || item.getStock() == 0) {
            return "Fields cannot be empty";
        }

        if (itemRepository.getItemById(item.getId()) != null) {
            String result = itemRepository.updateItem(item);
            if (result.contains("successfully")) {
                return result;
            }
            return result;
        }
        return "Item does not exist";
    }
    public String deleteItem(int id) {
        if (itemRepository.getItemById(id) != null) {
            String result = itemRepository.deleteItem(id);
            if (result.contains("successfully")) {
                return result;
            }
            return result;
        }
        return "Item does not exist";
    }
    public Item getItemById(int id) {
        return itemRepository.getItemById(id);
    }
    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }
    public void  updateStock(int itemId,int stock){
        itemRepository.updateStock(itemId,stock);
    }
}
