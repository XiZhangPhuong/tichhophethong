package com.example.fastfooddelivery2023.Model;

public class Order {
    private String id_User;
    private String id_Order;
    private String inf_user;
    private String inf_driver;
    private String inf_food;
    private String address;
    private String prince_Order;
    private int check;

    public Order(){

    }
    public Order(String id_User, String id_Order, String inf_user, String inf_driver, String inf_food, String address, String prince_Order, int check) {
        this.id_User = id_User;
        this.id_Order = id_Order;
        this.inf_user = inf_user;
        this.inf_driver = inf_driver;
        this.inf_food = inf_food;
        this.address = address;
        this.prince_Order = prince_Order;
        this.check = check;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getId_Order() {
        return id_Order;
    }

    public void setId_Order(String id_Order) {
        this.id_Order = id_Order;
    }

    public String getInf_user() {
        return inf_user;
    }

    public void setInf_user(String inf_user) {
        this.inf_user = inf_user;
    }

    public String getInf_driver() {
        return inf_driver;
    }

    public void setInf_driver(String inf_driver) {
        this.inf_driver = inf_driver;
    }

    public String getInf_food() {
        return inf_food;
    }

    public void setInf_food(String inf_food) {
        this.inf_food = inf_food;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrince_Order() {
        return prince_Order;
    }

    public void setPrince_Order(String prince_Order) {
        this.prince_Order = prince_Order;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
