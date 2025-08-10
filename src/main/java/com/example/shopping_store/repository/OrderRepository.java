package com.example.shopping_store.repository;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.model.Order;
import com.example.shopping_store.model.OrderItem;
import com.example.shopping_store.model.Status;
import com.example.shopping_store.repository.mapper.ItemMapper;
//import com.example.shopping_store.repository.mapper.OrderItemMapper;
import com.example.shopping_store.repository.mapper.OrderItemMapper;
import com.example.shopping_store.repository.mapper.OrderMapper;
import com.example.shopping_store.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final static String ORDERS_TABLE = "orders";
    private final static String ORDER_ITEMS_TABLE = "order_items";
    private final static String ITEMS_TABLE = "items";
    @Autowired
    private UserService userService;

    public Order saveOrder(String username,String shippingAddress) {
        String sql = "INSERT INTO " + ORDERS_TABLE + " (username, shipping_address, total_price) VALUES (?, ?, 0)";
        jdbcTemplate.update(sql, username, shippingAddress);

        return getOrderByUsernameByStatusTemp(username);


    }
    public void updateOrder(Order order) {

        String sql = "UPDATE " + ORDERS_TABLE + " SET  status = ?, shipping_address = ?, date_order = ?, total_price = ? WHERE  id = ?";
        jdbcTemplate.update(sql, order.getStatus().name(),order.getShippingAddress(),order.getDateOrder(),order.getTotal(),order.getId());

    }

    public void updateOrderToClose(String username,int orderId) {
        String sql = "UPDATE " + ORDERS_TABLE + " SET  status = 'CLOSE' WHERE  id = ?";
        jdbcTemplate.update(sql, orderId);

    }
    public Integer findOpenOrderIdByUsername(String username) {
        try {
            String sql = "SELECT id FROM " + ORDERS_TABLE + " WHERE username = ? AND status = 'TEMP' ";
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
            return id;
        } catch (Exception e) {
            return null;
        }
    }


    public List<Order> getOrderByUsernameAndStatusClose(String username) {
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE username = ? AND status = 'CLOSE' ";
      List<Order> orders = jdbcTemplate.query(sql, new OrderMapper(), username);
        for (Order order : orders) {
            List<OrderItem> items = getItemsForOrderHelper(order.getId());
            order.setItems(items);
        }
        return orders;
    }

    public void addItemToOrder(int orderId,int idItem) {
        String sql = "INSERT INTO " + ORDER_ITEMS_TABLE + " (order_id, item_id,quantity) VALUES (?, ?, 1)";
        jdbcTemplate.update(sql, orderId, idItem);

    }
    public Integer getItemIdFromOrderItemHelper(int orderId,int idItem) {
        String sql = "SELECT item_id FROM " + ORDER_ITEMS_TABLE + " WHERE order_id = ? AND item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, orderId, idItem);
        } catch (DataAccessException e) {
            return null;
        }
        }
    public void addToQuantity(int itemId,int orderId){
        String sql = "UPDATE " + ORDER_ITEMS_TABLE + " SET quantity = quantity + 1 WHERE item_id = ? AND order_id = ?";
        jdbcTemplate.update(sql, itemId,orderId);
    }

    public Order getOrderByUsernameByStatusTemp(String username) {
        try {

            String sqlOrder = "SELECT * FROM " + ORDERS_TABLE + " WHERE username = ? AND status = 'TEMP' LIMIT 1";
            List<Order> orders = jdbcTemplate.query(sqlOrder, new OrderMapper(), username);
            if (orders.isEmpty()){
                return null;
            }
            Order order = orders.getFirst();
            order.setItems(getItemsForOrderHelper(order.getId()));
            System.out.println("order TEMP: " + order);
            return order;
        } catch (Exception e) {
            return null;
        }
    }

    public List<OrderItem> getItemsForOrderHelper(int orderId) {
         String sqlItem = "SELECT i.* , oi.quantity FROM " + ITEMS_TABLE + " i " +
                 "JOIN " + ORDER_ITEMS_TABLE + " oi ON i.id = oi.item_id " +
                 "WHERE oi.order_id = ?";
         List<OrderItem> items = jdbcTemplate.query(sqlItem, new OrderItemMapper(),orderId);

         return items;
    }

    public List<Order> getCloseOrdersWithItems(String username) {
        List<Order> orders = getOrderByUsernameAndStatusClose(username);
        for (Order order : orders) {
            List<OrderItem> items = getItemsForOrderHelper(order.getId());
            order.setItems(items);
        }
        return orders;
    }

    public void deleteAllOrderUser(String username){
        String sql = "DELETE  FROM " + ORDERS_TABLE + " WHERE username = ?";
        jdbcTemplate.update(sql,username);
    }
    public void deleteItemOrder(int orderId){
        String sql = "DELETE FROM " + ORDER_ITEMS_TABLE + " WHERE order_id = ? ";
        jdbcTemplate.update(sql,orderId);
    }
    public void updateQuantityItem(int orderId,int itemId){
        String sql = "UPDATE " + ORDER_ITEMS_TABLE + " SET quantity = quantity - 1 WHERE item_id = ? AND order_id = ?";
        jdbcTemplate.update(sql,itemId,orderId);
    }
    public double getTotalPriceForOrder(int orderId) {
        String sql = "SELECT SUM(i.price * io.quantity) FROM " + ORDER_ITEMS_TABLE + " io JOIN " + ITEMS_TABLE + " i ON io.item_id = i.id WHERE order_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Double.class, orderId);
        } catch (DataAccessException e) {
            return 0.0;
        }
    }
    public Integer getQuantityFromOrderItem(int orderId, int itemId) {
        String sql = "SELECT quantity FROM " + ORDER_ITEMS_TABLE + " WHERE order_id = ? AND item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, orderId, itemId);
        } catch (DataAccessException e) {
            return null;
        }
    }
    public void deleteOrderItem(int orderId,int itemId){
        String sql = "DELETE FROM " + ORDER_ITEMS_TABLE + " WHERE order_id = ? AND item_id = ?";
        jdbcTemplate.update(sql,orderId,itemId);
    }
    public List<Order> getOrderByUsername(String username) {
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE username = ?";
        List<Order> orders = jdbcTemplate.query(sql, new OrderMapper(), username);
        for (Order order : orders) {
            List<OrderItem> items = getItemsForOrderHelper(order.getId());
            order.setItems(items);
        }
        return orders;
    }


}
