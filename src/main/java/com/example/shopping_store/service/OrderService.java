package com.example.shopping_store.service;


import com.example.shopping_store.model.Order;

import com.example.shopping_store.model.OrderItem;
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


    public void addItemToOrderAndOrder(String username, int itemId) {

        Order order = orderRepository.getOrderByUsernameByStatusTemp(username);
        System.out.println( "The order"+order);
        if (order == null){
            order =  orderRepository.saveOrder(username,userService.getAddressHelper(username));
        }
        System.out.println(order.getId());
        addItemToOrder(order.getId(), itemId);
            System.out.println(
                "Order number " + order );

    }
    public String updateOrderToClose(String username) {
        Order order = orderRepository.getOrderByUsernameByStatusTemp(username);
        if (order != null ) {
             orderRepository.updateOrderToClose(username, orderRepository.findOpenOrderIdByUsername(username));
            List<OrderItem> items = order.getItems();
            for (OrderItem item : items){
             itemService.updateStock(item.getId(),(itemService.getItemById(item.getId()).getStock() - item.getStock()));
                System.out.println("item name: " + item.getTitle() + "new stock " + item.getStock());
            }
            return "The order is close!";
        }
        return "user name don't have an open order";
    }


    public Order getOrderByUsernameByStatusTemp(String username ) {
        Order order = orderRepository.getOrderByUsernameByStatusTemp(username);
        if (order != null) {

            if (order.getItems().isEmpty()) {
                return null;
            }
            return order;
        }
        return null;
    }
    public List<Order> getAllOrdersByStatusCloseByUsername(String username) {
        return orderRepository.getCloseOrdersWithItems(username);
    }
    public void addItemToOrder(int idOrder, int itemId){
        Integer exist = orderRepository.getItemIdFromOrderItemHelper(idOrder, itemId);
        if(exist !=null && exist == itemId ){
            orderRepository.addToQuantity(itemId, idOrder);
            System.out.println("add quantity!");
        } else {
        orderRepository.addItemToOrder(idOrder, itemId);}
    }
    public String removeItemFromOrder( int idItem,String username) {
        Order order = orderRepository.getOrderByUsernameByStatusTemp(username);


        orderRepository.removeItemFromOrderNew(order.getId(), idItem);
return  "del";
    }

    public void deleteOrderByUsername(String username){
        orderRepository.deleteAllOrderUser(username);
    }

}
