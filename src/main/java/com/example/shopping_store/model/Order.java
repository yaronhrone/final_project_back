package com.example.shopping_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;
    private String username;
    @JsonProperty("date_order")
    private LocalDate dateOrder;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    private Status status;
    private List<OrderItem> items;
    @JsonProperty("total_price")
    private double total;

    public Order() {
    }

    public Order(int id,double total, String username, LocalDate dateOrder, String shippingAddress, Status status, List<OrderItem> items) {
        this.id = id;
        this.username = username;
        this.dateOrder = dateOrder;
        this.shippingAddress = shippingAddress;
        this.status = status;
         this.items = items;
        this.total = total;

    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + username + '\'' +
                ", dateOrder=" + dateOrder +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status=" + status + "orderItems=" + items + " total=" + total +
                '}';
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order(String username, String addressHelper, LocalDate now, Status temp) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDate dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
