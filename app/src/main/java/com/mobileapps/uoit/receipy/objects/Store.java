package com.mobileapps.uoit.receipy.objects;

import java.io.Serializable;

public class Store implements Serializable {
    private int id;
    private String name;
    private String address;
    private double total_price;
    private int total_items_not_found;

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Store(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Store(int id, String name, String address, double total_price, int total_items_not_found) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.total_price = total_price;
        this.total_items_not_found = total_items_not_found;
    }

    public int getTotal_items_not_found() {
        return total_items_not_found;
    }

    public void setTotal_items_not_found(int total_items_not_found) {
        this.total_items_not_found = total_items_not_found;
    }

    public void addOneUnfound() {
        total_items_not_found++;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void addToTotal(double price) {
        this.total_price += price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
