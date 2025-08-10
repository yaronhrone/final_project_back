package com.example.shopping_store.service;


import com.example.shopping_store.model.Item;
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


    public String addItemToOrderAndOrder(String username, int itemId) {

        Order order = orderRepository.getOrderByUsernameByStatusTemp(username);
        Item item = itemService.getItemById(itemId);
        if (item == null ) {
            return"Item not found in database";
        }
        if (order == null){
            order =  orderRepository.saveOrder(username,userService.getAddressHelper(username));
        }
        String result = addItemToOrder(order.getId(), itemId);


        return result;
    }
    public String updateOrderToClose(String username) {
        Order order = orderRepository.getOrderByUsernameByStatusTemp(username);
        if (order != null ) {
             orderRepository.updateOrderToClose(username, orderRepository.findOpenOrderIdByUsername(username));
            List<OrderItem> items = order.getItems();
            for (OrderItem orderItem : items){
                Item item = itemService.getItemById(orderItem.getId());

                int newStock = item.getStock() - orderItem.getQuantity();
                if (newStock < 0) {
                    return "not enough stock";
                }
                item.setStock(newStock);
                itemService.updateItem(item);

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
            double total = orderRepository.getTotalPriceForOrder(order.getId());
            order.setTotal(total);

            orderRepository.updateOrder(order);
            return order;
        }
        return null;
    }
    public List<Order> getAllOrdersByStatusCloseByUsername(String username) {
        return orderRepository.getOrderByUsernameAndStatusClose(username);
    }
    public String addItemToOrder(int idOrder, int itemId){
        Integer exist = orderRepository.getItemIdFromOrderItemHelper(idOrder, itemId);
        Item item = itemService.getItemById(itemId);
        Integer quantity = orderRepository.getQuantityFromOrderItem(idOrder, itemId);
        if (quantity == null) {
            quantity = 0;
        }
        if ( quantity >= item.getStock() || item.getStock() == 0  ) {
            return "Item out of stock";
        }

        if(exist !=null && exist == itemId ){
            orderRepository.addToQuantity(itemId, idOrder);

        } else {
        orderRepository.addItemToOrder(idOrder, itemId);}
        return "Item added to order";
    }
    public String removeItemFromOrder( int idItem,String username) {
        Order order = orderRepository.getOrderByUsernameByStatusTemp(username);
        if (order == null) {
            return "user name don't have an open order";
        }
        Integer quantity = orderRepository.getQuantityFromOrderItem(order.getId(), idItem);
        if (quantity == null) {
            return "not item in order exist";
    }    if (quantity > 1) {
        orderRepository.updateQuantityItem(order.getId(), idItem);
    } else {
        orderRepository.deleteOrderItem(order.getId(), idItem);
        }
return  "delete item from order";
    }

    public void deleteOrderByUsername(String username){
        List<Order> orders = orderRepository.getOrderByUsername(username);
        for (Order order : orders) {
            orderRepository.deleteItemOrder(order.getId());

        }
        orderRepository.deleteAllOrderUser(username);
    }

}
