package com.example.shopping_store.service;


import com.example.shopping_store.model.Order;
import com.example.shopping_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    public String saveOrder(Order order) {
        if (userService.getUserByUsername(order.getUsername()) != null) {

        return orderRepository.saveOrder(order);
        }
        return "User does not exist";
    }
    public String updateOrder(Order order ) {
        if (userService.getUserByUsername(order.getUsername()) != null) {
            return orderRepository.updateOrder(order);
        }
        return "User does not exist";
    }
    public String deleteOrder(String username) {
        if (userService.getUserByUsername(username) != null) {
            return orderRepository.deleteOrder(username);
        }
        return "User does not exist";
    }
    public Order getOrderByUsername(String username ) {
        return orderRepository.getOrderByUsername(username);
    }
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }
}
