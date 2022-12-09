package com.example.fastfooddelivery2023.Model;

public class Comment_FB {
    private String id_Food;
    private User user;
    private String information;
    private String date;
    private int like;

    public Comment_FB(String id_Food, User user, String information, String date, int like) {
        this.id_Food = id_Food;
        this.user = user;
        this.information = information;
        this.date = date;
        this.like = like;
    }

    public Comment_FB(){

    }

    public String getId_Food() {
        return id_Food;
    }

    public void setId_Food(String id_Food) {
        this.id_Food = id_Food;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
