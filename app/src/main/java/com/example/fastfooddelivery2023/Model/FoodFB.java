package com.example.fastfooddelivery2023.Model;

public class FoodFB {
    private String id,name,image,category,information,price;

    public FoodFB() {
    }

    public FoodFB(String id, String name, String image, String category, String information, String price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.information = information;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
