package com.example.fastfooddelivery2023.Model;

import java.io.Serializable;

public class Food implements Serializable {
    private String id_Food;
    private String name_Food;
    private String image_Food;
    private String category_Food;
    private String information_Food;
    private String comment_Food;
    private String like;
    private int quantity;
    private double price_Food;


    public Food(String id_Food, String name_Food, String image_Food, String category_Food, String information_Food, String comment_Food, String like, int quantity, double price_Food) {
        this.id_Food = id_Food;
        this.name_Food = name_Food;
        this.image_Food = image_Food;
        this.category_Food = category_Food;
        this.information_Food = information_Food;
        this.comment_Food = comment_Food;
        this.like = like;
        this.quantity = quantity;
        this.price_Food = price_Food;
    }

    public Food() {
    }

    public String getId_Food() {
        return id_Food;
    }

    public void setId_Food(String id_Food) {
        this.id_Food = id_Food;
    }

    public String getName_Food() {
        return name_Food;
    }

    public void setName_Food(String name_Food) {
        this.name_Food = name_Food;
    }

    public String getImage_Food() {
        return image_Food;
    }

    public void setImage_Food(String image_Food) {
        this.image_Food = image_Food;
    }

    public String getCategory_Food() {
        return category_Food;
    }

    public void setCategory_Food(String category_Food) {
        this.category_Food = category_Food;
    }

    public String getInformation_Food() {
        return information_Food;
    }

    public void setInformation_Food(String information_Food) {
        this.information_Food = information_Food;
    }

    public String getComment_Food() {
        return comment_Food;
    }

    public void setComment_Food(String comment_Food) {
        this.comment_Food = comment_Food;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice_Food() {
        return price_Food;
    }

    public void setPrice_Food(double price_Food) {
        this.price_Food = price_Food;
    }
}
