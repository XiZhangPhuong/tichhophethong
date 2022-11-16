package com.example.fastfooddelivery2023.Model;

public class Comment {
    private String name,comment,date;
    private int quantityLike;

    public Comment() {
    }

    public Comment(String name, String comment, String date, int quantityLike) {
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.quantityLike = quantityLike;
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
