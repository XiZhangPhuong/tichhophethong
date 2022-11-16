package com.example.fastfooddelivery2023.Model;

public class User {
    private int id;
    private String fullName,phoneNumber,passWord;

    public User() {
    }

    public User(int id, String fullName, String phoneNumber, String passWord) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
