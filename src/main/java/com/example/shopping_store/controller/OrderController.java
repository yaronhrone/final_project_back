package com.example.shopping_store.controller;

import com.example.shopping_store.model.Order;
import com.example.shopping_store.service.OrderService;
import com.example.shopping_store.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add_order")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> addOrder(@RequestHeader(value = "Authorization") String token,@RequestParam int idItem) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            orderService.addItemToOrder(username,idItem);

            return new ResponseEntity<>("Order saved", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get_orders")
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
        } catch (Exception e) {
        return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update_order/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateOrder(@RequestHeader(value = "Authorization") String token, @RequestBody Order order) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            order.setUsername(username);
            String result = orderService.updateOrder(order);
            if (result.contains("successfully")) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete_order/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteOrder(@RequestHeader(value = "Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            String result = orderService.deleteOrder(username);
            if (result.contains("successfully")) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get_order")
    public ResponseEntity<Order> getOrder(@RequestHeader(value = "Authorization") String token) {

        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            return new ResponseEntity<>(orderService.getOrderByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get_order1")
    public ResponseEntity<Order> getOrderById(@RequestHeader(value = "Authorization") String token){
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            return new ResponseEntity<>(orderService.getOrderById(username),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("add_item")
    public ResponseEntity addItemToOrder(@RequestHeader(value = "Authorization") String token,@RequestParam int orderId,@RequestParam int idItem){
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            orderService.addItemToOrder(orderId,idItem);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
