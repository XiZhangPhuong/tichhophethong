package com.example.fastfooddelivery2023.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Order_FB  implements Parcelable {
    private String id_order;
    private User user;
    private Staff staff;
    private String address_order;
    private List<Food> listFood;
    private String time_order;
    private double total_cart;
    private String payments;
    private int check;

    public Order_FB(String id_order, User user, Staff staff, String address_order, List<Food> listFood, String time_order, double total_cart, String payments, int check) {
        this.id_order = id_order;
        this.user = user;
        this.staff = staff;
        this.address_order = address_order;
        this.listFood = listFood;
        this.time_order = time_order;
        this.total_cart = total_cart;
        this.payments = payments;
        this.check = check;
    }

    public Order_FB() {
    }

    protected Order_FB(Parcel in) {
        id_order = in.readString();
        address_order = in.readString();
        listFood = in.createTypedArrayList(Food.CREATOR);
        time_order = in.readString();
        total_cart = in.readDouble();
        payments = in.readString();
        check = in.readInt();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_order);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(staff, flags);
        dest.writeString(address_order);
        dest.writeTypedList(listFood);
        dest.writeString(time_order);
        dest.writeDouble(total_cart);
        dest.writeString(payments);
        dest.writeInt(check);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order_FB> CREATOR = new Creator<Order_FB>() {
        @Override
        public Order_FB createFromParcel(Parcel in) {
            return new Order_FB(in);
        }

        @Override
        public Order_FB[] newArray(int size) {
            return new Order_FB[size];
        }
    };

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

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }




    @Override
    public String toString() {
        return "Order_FB{" +
                "id_order='" + id_order + '\'' +
                ", user=" + user +
                ", staff=" + staff +
                ", address_order='" + address_order + '\'' +
                ", listFood=" + listFood +
                ", time_order='" + time_order + '\'' +
                ", total_cart=" + total_cart +
                ", payments='" + payments + '\'' +
                ", check=" + check +
                '}';
    }
}
