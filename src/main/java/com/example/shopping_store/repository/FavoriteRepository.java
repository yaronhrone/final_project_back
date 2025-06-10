package com.example.shopping_store.repository;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.repository.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String FAVORITE_ITEMS_TABLE = "favorites";
    private final static String ITEMS_TABLE = "items";

    public List<Item> getFavoriteItemsByUsername(String username) {
        String sql = "SELECT i.* FROM " + FAVORITE_ITEMS_TABLE + " f JOIN " + ITEMS_TABLE + " i ON f.item_id = i.id WHERE f.username = ?";
        return jdbcTemplate.query(sql, new ItemMapper(), username);
    }
    public  void addFavoriteItemToUser(String username, int itemId) {
        String sql = "INSERT INTO " + FAVORITE_ITEMS_TABLE + " (username, item_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, username, itemId);
    }
    public void removeFavoriteItemFromUser(String username, int itemId) {
        String sql = "DELETE FROM " + FAVORITE_ITEMS_TABLE + " WHERE username = ? AND item_id = ?";
        jdbcTemplate.update(sql, username, itemId);

    }
}
