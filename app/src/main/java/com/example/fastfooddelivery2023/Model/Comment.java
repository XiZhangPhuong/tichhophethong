package com.example.fastfooddelivery2023.Model;

public class Comment {
    private String idFood;
    private String name,comment,date;
    private int quantityLike;

    public Comment() {
    }

    public Comment(String idFood, String name, String comment, String date, int quantityLike) {
        this.idFood = idFood;
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.quantityLike = quantityLike;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantityLike() {
        return quantityLike;
    }

    public void setQuantityLike(int quantityLike) {
        this.quantityLike = quantityLike;
    }
}
