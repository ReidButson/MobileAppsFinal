package com.mobileapps.uoit.receipy;

public class Ingredient {
    public int id;
    public String name;
    public int amount;
    public String units;
    public double price;

    public Ingredient(int id, String name, int amount, String units, double price){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.units = units;
        this.price = price;
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

    public int getAmount(){
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
