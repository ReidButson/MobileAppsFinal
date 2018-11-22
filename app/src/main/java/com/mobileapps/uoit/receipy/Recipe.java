package com.mobileapps.uoit.receipy;

import java.util.ArrayList;

public class Recipe {
    public String name;
    public int id;
    public ArrayList<Ingredient> ingredients;
    public Recipe(int id, String name, ArrayList<Ingredient> ingredients){
        this.ingredients = new ArrayList<Ingredient>();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }
    public Recipe(int id, String name){
        this.ingredients = new ArrayList<Ingredient>();
        this.id = id;
        this.name = name;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }
    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void removeIngredient(Ingredient ingredient){
        ingredients.remove(ingredient);
    }
}
