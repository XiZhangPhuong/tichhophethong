package com.example.fastfooddelivery2023.Model;

public class User {
    private String id;
    private String fullName,phoneNumber,passWord;

    public User() {
    }

    public User(String id, String fullName, String phoneNumber, String passWord) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
