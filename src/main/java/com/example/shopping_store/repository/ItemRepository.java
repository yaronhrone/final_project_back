package com.example.shopping_store.repository;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.repository.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String ITEMS_TABLE = "items";

    public String saveItem(Item item) {
        String sql = "INSERT INTO " + ITEMS_TABLE + " (title, photo, price, stock) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, item.getTitle(), item.getPhoto(), item.getPrice(), item.getStock());
        return "Item saved successfully";
    }

    public String updateItem(Item item) {
        String sql = "UPDATE " + ITEMS_TABLE + " SET title = ?, photo = ?, price = ?, stock = ? WHERE id = ?";
        jdbcTemplate.update(sql, item.getTitle(), item.getPhoto(), item.getPrice(), item.getStock(), item.getId());
        return "Item updated successfully";
    }
    public String deleteItem(int id) {
        String sql = "DELETE FROM " + ITEMS_TABLE + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return "Item deleted successfully";
    }
    public Item getItemById(int id) {
        String sql = "SELECT * FROM " + ITEMS_TABLE + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ItemMapper(), id);
    }
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM " + ITEMS_TABLE;
        return jdbcTemplate.query(sql, new ItemMapper());
    }
    public List<Item> searchItems(String title) {
        String sql = "SELECT * FROM " + ITEMS_TABLE + " WHERE LOWER(title) LIKE LOWER(?)";

        return jdbcTemplate.query(sql, new ItemMapper(), "%" + title + "%");
    }

}
