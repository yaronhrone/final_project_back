package com.example.shopping_store.model;

public class Item {
    private int id;
    private String title;
    private String photo;
    private double price;
    private int stock;

    public Item() {
    }

    public Item(int id, String title, String photo, double price, int stock) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Items{" +
                "title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
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
}
