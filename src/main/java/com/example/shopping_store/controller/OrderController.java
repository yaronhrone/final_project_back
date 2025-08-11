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

    @PostMapping("/add_item")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> addOrder(@RequestHeader(value = "Authorization") String token,@RequestParam int itemId) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            String result =  orderService.addItemToOrderAndOrder(username,itemId);
            if (result.contains("added")){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove_item_order")
    public ResponseEntity<String> removeItemFromOrder(@RequestHeader (value = "Authorization") String token,@RequestParam int itemId){
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            return new ResponseEntity<>(orderService.removeItemFromOrder(itemId,username),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get_orders")
    public ResponseEntity<List<Order>> getOrdersByCloseOrder(@RequestHeader (value = "Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            return new ResponseEntity<>(orderService.getAllOrdersByStatusCloseByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
        return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update_order")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateOrder(@RequestHeader(value = "Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            String result = orderService.updateOrderToClose(username);
                return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get_order_temp")
    public ResponseEntity<Order> getOrder(@RequestHeader(value = "Authorization") String token) {

        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            Order order = orderService.getOrderByUsernameByStatusTemp(username);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }

            return new ResponseEntity("No order open",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
