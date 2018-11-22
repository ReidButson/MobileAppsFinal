package com.mobileapps.uoit.receipy;

public class Ingredient {
    public int id;
    public int amount;
    public String units;
    public Ingredient(int id, int amount, String units){
        this.id = id;
        this.amount = amount;
        this.units = units;
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
