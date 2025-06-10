package com.example.shopping_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteRequest {
    @JsonProperty("item_id")
    private int itemId;
    @JsonProperty("user_id")
    private int userId;

    public FavoriteRequest(int itemId, int userId) {
        this.itemId = itemId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FavoriteRequest{" +
                "itemId=" + itemId +
                ", userId=" + userId +
                '}';
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
