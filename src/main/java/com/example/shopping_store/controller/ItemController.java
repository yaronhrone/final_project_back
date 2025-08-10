package com.example.shopping_store.controller;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.service.ItemService;
import com.example.shopping_store.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add_item")
    public ResponseEntity<String> addItem(@RequestBody Item item ) {
        try {

            itemService.saveItem(item);
            return new ResponseEntity<>("Item added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding item: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update_item")
    public ResponseEntity<String> updateItem(@RequestBody Item item) {
        try {
            itemService.updateItem(item);
            return new ResponseEntity<>("Item updated successfully",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( "Error updating item: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete_item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>( "Item deleted successfully",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting item: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        try {
            return new ResponseEntity<>( itemService.getItemById(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get_all_items")
    public ResponseEntity<List<Item>> getAllItems() {
        try {
        return new ResponseEntity<>(itemService.getAllItems(),HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Item>> getItemByName(@PathVariable String name) {
            System.out.println("get item by name");
        try {
            return new ResponseEntity<>(itemService.searchItems(name),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("error");
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
