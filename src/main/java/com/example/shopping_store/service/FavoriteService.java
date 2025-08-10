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
    public String  addFavoriteItemToUser(String username, int itemId) {
        if (userService.getUserByUsername(username) == null) {
            return "User not found" ;
        }if (favoriteRepository.getFavoriteItemsByUsername(username).stream().anyMatch(item -> item.getId() == itemId)) {

            return  "Item already in favorites";
        }

        if (itemService.getItemById(itemId) == null) {
            return "Item not found";
        }


        favoriteRepository.addFavoriteItemToUser(username, itemId);
        return "Item added to favorites";
    }
    public List<Item> getFavoriteItemsByUsername(String username) {
        if (userService.getUserByUsername(username) == null) {
            throw new IllegalArgumentException("User not found");
        }
        return favoriteRepository.getFavoriteItemsByUsername(username);
    }
    public String removeFavoriteItemFromUser(String username, int itemId) {
        if (userService.getUserByUsername(username) == null) {
            return "User not found";
        }
        if (itemService.getItemById(itemId) == null || favoriteRepository.getFavoriteItemsByUsername(username).stream().noneMatch(item -> item.getId() == itemId)) {
            System.out.println(favoriteRepository.getFavoriteItemsByUsername(username).stream().noneMatch(item -> item.getId() == itemId));
            return "Item not found";
        }
        favoriteRepository.removeFavoriteItemFromUser(username, itemId);
        return "Item removed from favorites";
    }
    public List<Item> getFavoritesItemsBySearch(String title, String username) {
        return favoriteRepository.searchItemsFromFavorites(username,title);
    }
    public void deleteFavoriteItemFromUser(String username) {
        favoriteRepository.deleteAllFavorites(username);
    }
}
