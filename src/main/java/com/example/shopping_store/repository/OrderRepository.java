package com.example.shopping_store.repository;


import com.example.shopping_store.model.Item;
import com.example.shopping_store.model.Order;
import com.example.shopping_store.model.OrderItem;
import com.example.shopping_store.model.Status;
import com.example.shopping_store.repository.mapper.ItemMapper;
import com.example.shopping_store.repository.mapper.OrderItemMapper;
import com.example.shopping_store.repository.mapper.OrderMapper;
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

    public Integer saveOrder(String username,String shippingAddress) {
        String sql = "INSERT INTO " + ORDERS_TABLE + " (username, shipping_address) VALUES (?, ?)";
        jdbcTemplate.update(sql, username, shippingAddress);
        return findOpenOrderIdByUsername(username);

    }

    public String updateOrder(Order order) {
        String sql = "UPDATE " + ORDERS_TABLE + " SET date_order = ?, shipping_address = ?, status = ? WHERE  username = ?";
        jdbcTemplate.update(sql,  order.getDateOrder(), order.getShippingAddress(), order.getStatus().name(),order.getUsername());
        return "Order updated successfully";
    }
    public Integer findOpenOrderIdByUsername(String username) {
        String sql = "SELECT id FROM " + ORDERS_TABLE + " WHERE username = ? AND status = 'TEMP' LIMIT 1";
        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class, username);
        return ids.isEmpty() ? null : ids.get(0);
    }

    public Order getOrderByUsername(String username) {
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new OrderMapper(), username);
    }
    public String deleteOrder(String username) {
        String sql = "DELETE FROM " + ORDERS_TABLE + " WHERE username = ?";
            jdbcTemplate.update(sql, username);
        return "Order deleted successfully";
    }
    public String updateOrderStatus(int id, Status status) {
        String sql = "UPDATE " + ORDERS_TABLE + " SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
        return "Order status updated successfully";
    }
    public void addItemToOrder(int orderId,int idItem) {
        String sql = "INSERT INTO " + ORDER_ITEMS_TABLE + " (order_id, item_id,quantity) VALUES (?, ?, 1)";
        jdbcTemplate.update(sql, orderId, idItem);
//        return "Item added to order successfully";
    }
    public Integer getItemIdFromOrderItem(int orderId,int idItem) {
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
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM " + ORDERS_TABLE;
        return jdbcTemplate.query(sql, new OrderMapper());
    }
    public Order getOrderById(String username){
        String sqlOrder = "SELECT * FROM " + ORDERS_TABLE + " WHERE username = ?";
        Order order = jdbcTemplate.queryForObject(sqlOrder, new OrderMapper(),username);

         String sqlItem = "SELECT i.* FROM " + ITEMS_TABLE + " i " +
                 "JOIN " + ORDER_ITEMS_TABLE + " oi ON i.id = oi.item_id " +
                 "WHERE oi.order_id = ?";
         List<Item> items = jdbcTemplate.query(sqlItem, new ItemMapper(),order.getId());

         order.setItems(items);
         return order;
    }


}
