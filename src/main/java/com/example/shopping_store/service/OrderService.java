package com.example.shopping_store.service;


import com.example.shopping_store.model.Order;
import com.example.shopping_store.model.Status;
import com.example.shopping_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;


    public void addItemToOrder(String username, int itemId) {

        Integer openOrderId = orderRepository.findOpenOrderIdByUsername(username);

        if (openOrderId == null) {
            openOrderId = orderRepository.saveOrder(username,userService.getAddressHelper(username));
        }
        orderRepository.addItemToOrder(openOrderId, itemId);

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
    public Order getOrderById (String username){
        return orderRepository.getOrderById(username);
    }
    public void addItemToOrder(int idOrder, int itemId){
        Integer exist = orderRepository.getItemIdFromOrderItem(idOrder, itemId);
        if(exist !=null && exist == itemId ){
            orderRepository.addToQuantity(idOrder, itemId);
        } else {
        orderRepository.addItemToOrder(idOrder, itemId);}
    }
}
