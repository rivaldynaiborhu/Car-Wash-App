package com.example.carwashapps.model;

import android.os.Parcel;
import android.os.Parcelable;



public class Carwash implements Parcelable {
    private String name, address,website, phone, openHours, carwashId;

    public Carwash() {
    }


    protected Carwash(Parcel in) {
        name = in.readString();
        address = in.readString();
        website = in.readString();
        phone = in.readString();
        openHours = in.readString();
        carwashId = in.readString();
    }

    public static final Creator<Carwash> CREATOR = new Creator<Carwash>() {
        @Override
        public Carwash createFromParcel(Parcel in) {
            return new Carwash(in);
        }

        @Override
        public Carwash[] newArray(int size) {
            return new Carwash[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public String getCarwashId() {
        return carwashId;
    }

    public void setCarwashId(String carwashId) {
        this.carwashId = carwashId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(website);
        parcel.writeString(phone);
        parcel.writeString(openHours);
        parcel.writeString(carwashId);
    }
}
