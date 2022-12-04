package com.example.fastfooddelivery2023.Model;

public class Staff {
    private String id_staff;
    private String fullName_staff;
    private String phoneNumber;

    public Staff(String id_staff, String fullName_staff, String phoneNumber) {
        this.id_staff = id_staff;
        this.fullName_staff = fullName_staff;
        this.phoneNumber = phoneNumber;
    }

    public Staff() {
    }

    public String getId_staff() {
        return id_staff;
    }

    public void setId_staff(String id_staff) {
        this.id_staff = id_staff;
    }

    public String getFullName_staff() {
        return fullName_staff;
    }

    public void setFullName_staff(String fullName_staff) {
        this.fullName_staff = fullName_staff;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
