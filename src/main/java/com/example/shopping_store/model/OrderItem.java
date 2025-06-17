package com.example.shopping_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderItem {
    private int id;
    @JsonProperty("item_id")
    private int itemId;
    @JsonProperty("order_id")
    private int orderId;
    private int quantity;

    public OrderItem(int id, int itemId, int orderId, int quantity) {
        this.id = id;
        this.itemId = itemId;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", userId=" + orderId +
                ", quantity=" + quantity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderItem() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
