package com.mobileapps.uoit.receipy;

public class Store {
    private int id;
    private String name;
    private String address;
    private double price = 0;
    private int items_not_found = 0;

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Store(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItems_not_found() {
        return items_not_found;
    }

    public void setItems_not_found(int items_not_found) {
        this.items_not_found = items_not_found;
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
