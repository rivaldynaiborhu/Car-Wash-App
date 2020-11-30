package com.example.carwashapps.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Carwash implements Parcelable {
    private String name, address, carwashId;

    public Carwash() {
    }

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

    public String getCarwashId() {
        return carwashId;
    }

    public void setCarwashId(String carwashId) {
        this.carwashId = carwashId;
    }

    protected Carwash(Parcel in) {
        name = in.readString();
        address = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(carwashId);
    }
}
