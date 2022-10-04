package com.example.greenlife.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartOrder {
    String id;
    String userId;
    String treeName;
    String imageUrl;
    double price;
    int amount;
    public CartOrder(){
        // default
    }

    public CartOrder(String id, String treeName,String userId, String imageUrl, double price, int amount) {
        this.id = id;
        this.userId = userId;
        this.treeName = treeName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public double getPrice() {
        return price;
    }

    public String getTreeName() {
        return treeName;
    }

    public int getAmount() {
        return amount;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("userId",userId);
        result.put("imageUrl", imageUrl);
        result.put("price", price);
        result.put("amount", amount);
        return result;
    };
}
