package com.mobileapps.uoit.receipy;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Ingredient implements Parcelable {
    public int id;
    public String name;
    public double amount;
    public String units;
    public double price;

    public Ingredient(int id, String name, double amount, String units, double price){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.units = units;
        this.price = price;
    }

    public Ingredient(String name, double amount, String units, double price){
        this.name = name;
        this.amount = amount;
        this.units = units;
        this.price = price;
    }

    public Ingredient(int id, String name, double amount, String units) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.units = units;
    }

    public Ingredient(int id, String name, String units) {
        this.id = id;
        this.name = name;
        this.units = units;
    }

    public Ingredient(String name, String units){
        this.name = name;
        this.units = units;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.amount = in.readDouble();
        this.units = in.readString();
        this.price = in.readDouble();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public double getAmount(){
        return this.amount;
    }

    public String getUnits(){
        return this.units;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setUnits(String units){
        this.units = units;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.amount);
        dest.writeString(this.units);
        dest.writeDouble(this.price);
    }
}
