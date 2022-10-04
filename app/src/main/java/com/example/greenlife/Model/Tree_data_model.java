package com.example.greenlife.Model;

import java.util.HashMap;
import java.util.Map;

public class Tree_data_model {
    String id;
    String name;
    String imageUrl;
    String type;
    double price;
    String location;
    String description;
    boolean isFavorite;

    public Tree_data_model(){
    }
    public Tree_data_model(String id,String name,String imageUrl ,String type,double price,String location, String description, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.price = price;
        this.location = location;
        this.description = description;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }


    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("imageUrl", imageUrl);
        result.put("type", type);
        result.put("price", price);
        result.put("location", location);
        result.put("description", description);
        result.put("isFavorite", isFavorite);
        return result;
    }
}
