package com.example.shopping_store.model;


public class FavoriteResponse {
    private Item items;

    public FavoriteResponse(Item items) {
        this.items = items;
    }

    public void setItems(Item items) {
        this.items = items;
    }

    public FavoriteResponse() {
    }

    public Item getItems() {
        return items;
    }
}
