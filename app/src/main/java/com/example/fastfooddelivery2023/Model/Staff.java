package com.example.fastfooddelivery2023.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Staff implements Parcelable {
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

    protected Staff(Parcel in) {
        id_staff = in.readString();
        fullName_staff = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_staff);
        parcel.writeString(fullName_staff);
        parcel.writeString(phoneNumber);
    }
}
