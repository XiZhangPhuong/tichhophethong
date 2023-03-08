package com.example.fastfooddelivery2023.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
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

    protected User(Parcel in) {
        id = in.readString();
        fullName = in.readString();
        phoneNumber = in.readString();
        passWord = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(fullName);
        parcel.writeString(phoneNumber);
        parcel.writeString(passWord);
    }
}
