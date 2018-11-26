package com.mobileapps.uoit.receipy;

public class Ingredient {
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

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setUnits(String units){
        this.units = units;
    }
}
