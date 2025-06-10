package com.example.shopping_store.repository;


import com.example.shopping_store.model.Order;
import com.example.shopping_store.model.Status;
import com.example.shopping_store.repository.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final static String ORDERS_TABLE = "orders";

    public String saveOrder(Order order) {
        String sql = "INSERT INTO " + ORDERS_TABLE + " (username, date_order, shipping_address, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getUsername(), order.getDateOrder(), order.getShippingAddress(), order.getStatus());
        return "Order saved successfully";

    }
    public String updateOrder(Order order) {
        String sql = "UPDATE " + ORDERS_TABLE + " SET date_order = ?, shipping_address = ?, status = ? WHERE  username = ?";
        jdbcTemplate.update(sql,  order.getDateOrder(), order.getShippingAddress(), order.getStatus(),order.getUsername());
        return "Order updated successfully";
    }
    public Order getOrderByUsername(String username) {
        String sql = "SELECT * FROM orders WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new OrderMapper(), username);
    }
    public String deleteOrder(String username) {
        String sql = "DELETE FROM orders WHERE username = ?";
            jdbcTemplate.update(sql, username);
        return "Order deleted successfully";
    }
    public String updateOrderStatus(int id, Status status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
        return "Order status updated successfully";
    }
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, new OrderMapper());
    }

}
