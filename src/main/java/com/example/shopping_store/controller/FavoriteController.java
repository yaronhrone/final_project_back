package com.example.shopping_store.controller;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.service.FavoriteService;
import com.example.shopping_store.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("favorite")
@CrossOrigin("http://localhost:3000")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/{idItem}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> addFavorite(@RequestHeader(value = "Authorization") String token,@PathVariable int idItem) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            String message = favoriteService.addFavoriteItemToUser(username, idItem);
            if (message.contains("added")) {
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            } return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{idItem}")
    public ResponseEntity<String> deleteFavorite(@RequestHeader(value = "Authorization") String token,@PathVariable int idItem) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            String result = favoriteService.removeFavoriteItemFromUser(username, idItem);
            if (result.contains("removed")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
            } return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Item>> getFavoriteItems(@RequestHeader(value = "Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            System.out.println("username from jwt: " + username);
            return new ResponseEntity<>(favoriteService.getFavoriteItemsByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Item>> searchFavoriteItems(@RequestHeader(value = "Authorization") String token,@PathVariable String title) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            return new ResponseEntity<>(favoriteService.getFavoritesItemsBySearch(username, title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
