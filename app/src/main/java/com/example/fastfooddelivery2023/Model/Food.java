package com.example.fastfooddelivery2023.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Food implements Parcelable {
    private String id_Food;
    private String name_Food;
    private String image_Food;
    private String category_Food;
    private String information_Food;
    private List<Comment> listComment;
    private String like;
    private int quantity;
    private double price_Food;


    public Food(){

    }

    public Food(String id_Food, String name_Food, String image_Food, String category_Food, String information_Food, List<Comment> listComment, String like, int quantity, double price_Food) {
        this.id_Food = id_Food;
        this.name_Food = name_Food;
        this.image_Food = image_Food;
        this.category_Food = category_Food;
        this.information_Food = information_Food;
        this.listComment = listComment;
        this.like = like;
        this.quantity = quantity;
        this.price_Food = price_Food;
    }

    protected Food(Parcel in) {
        id_Food = in.readString();
        name_Food = in.readString();
        image_Food = in.readString();
        category_Food = in.readString();
        information_Food = in.readString();
        like = in.readString();
        quantity = in.readInt();
        price_Food = in.readDouble();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_Food);
        parcel.writeString(name_Food);
        parcel.writeString(image_Food);
        parcel.writeString(category_Food);
        parcel.writeString(information_Food);
        parcel.writeString(like);
        parcel.writeInt(quantity);
        parcel.writeDouble(price_Food);
    }
}
