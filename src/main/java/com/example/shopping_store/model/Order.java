package com.example.shopping_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jshell.Snippet;

import java.time.LocalDate;

public class Order {
    private int id;
    private String username;
    @JsonProperty("date_order")
    private LocalDate dateOrder;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    private Status status;

    public Order(int id, String username, LocalDate dateOrder, String shippingAddress, Status status) {
        this.id = id;
        this.username = username;
        this.dateOrder = dateOrder;
        this.shippingAddress = shippingAddress;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + username + '\'' +
                ", dateOrder=" + dateOrder +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order() {
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
