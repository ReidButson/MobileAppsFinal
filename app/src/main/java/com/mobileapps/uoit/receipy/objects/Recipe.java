package com.mobileapps.uoit.receipy.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Parcelable {
    public String name;

    public int id;

    public ArrayList<Ingredient> ingredients;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.readArrayList(Ingredient.class.getClassLoader());
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
    public void setRecipe(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
    }
}
