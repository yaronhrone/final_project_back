package com.example.shopping_store.repository.mapper;

import com.example.shopping_store.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem order = new OrderItem();
        order.setId(rs.getInt("id"));
        order.setOrderId(rs.getInt("order_id"));
        order.setItemId(rs.getInt("item_id"));
        order.setQuantity(rs.getInt("quantity"));
        return order;
    }
}
