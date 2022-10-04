package com.example.greenlife.Model;

import java.util.List;

public class OrderModel {
    String id;
    String userId;
    double price;
    int amount;
    List<CartOrder> cart;
    public OrderModel(){

    }
    public OrderModel(String id, String userId, double price, int amount, List<CartOrder> cart) {
        this.id = id;
        this.userId = userId;
        this.price = price;
        this.amount = amount;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getUserId() {
        return userId;
    }

    public List<CartOrder> getCart() {
        return cart;
    }
}
