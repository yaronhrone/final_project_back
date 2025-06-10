package com.example.shopping_store.service;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    public void addFavoriteItemToUser(String username, int itemId) {
        if (userService.getUserByUsername(username) == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (itemService.getItemById(itemId) == null) {
            throw new IllegalArgumentException("Item not found");
        }


        favoriteRepository.addFavoriteItemToUser(username, itemId);
    }
    public List<Item> getFavoriteItemsByUsername(String username) {
        if (userService.getUserByUsername(username) == null) {
            throw new IllegalArgumentException("User not found");
        }
        return favoriteRepository.getFavoriteItemsByUsername(username);
    }
    public void removeFavoriteItemFromUser(String username, int itemId) {
        if (userService.getUserByUsername(username) == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (itemService.getItemById(itemId) == null) {
            throw new IllegalArgumentException("Item not found");
        }
        favoriteRepository.removeFavoriteItemFromUser(username, itemId);
    }
}
