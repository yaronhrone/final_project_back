package com.example.shopping_store.repository.mapper;

import com.example.shopping_store.model.Order;
import com.example.shopping_store.model.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();
        order.setUsername(rs.getString("username"));
        order.setDateOrder(rs.getDate("date_order").toLocalDate());
        order.setShippingAddress(rs.getString("shipping_address"));
        Status.valueOf(rs.getString("status"));
        return order;
    }
}
