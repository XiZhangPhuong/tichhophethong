package com.example.fastfooddelivery2023.Model;

import java.util.List;

public class Order_FB {
    private String id_order;
    private User user;
    private Staff staff;
    private String address_order;
    private List<Food> listFood;
    private String time_order;
    private double total_cart;
    private int check;

    public Order_FB(String id_order, User user, Staff staff, String address_order, List<Food> listFood, String time_order, double total_cart, int check) {
        this.id_order = id_order;
        this.user = user;
        this.staff = staff;
        this.address_order = address_order;
        this.listFood = listFood;
        this.time_order = time_order;
        this.total_cart = total_cart;
        this.check = check;
    }

    public Order_FB() {
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getAddress_order() {
        return address_order;
    }

    public void setAddress_order(String address_order) {
        this.address_order = address_order;
    }

    public List<Food> getListFood() {
        return listFood;
    }

    public void setListFood(List<Food> listFood) {
        this.listFood = listFood;
    }

    public String getTime_order() {
        return time_order;
    }

    public void setTime_order(String time_order) {
        this.time_order = time_order;
    }

    public double getTotal_cart() {
        return total_cart;
    }

    public void setTotal_cart(double total_cart) {
        this.total_cart = total_cart;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
