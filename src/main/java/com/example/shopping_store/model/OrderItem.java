package com.example.shopping_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class OrderItem {
    private int id;
    private String title;
    private String photo;
    private double price;
    private int stock;
    private int quantity;

    public OrderItem() {}

    public OrderItem(int id, String title, String photo, double price, int stock, int quantity) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.price = price;
        this.stock = stock;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}