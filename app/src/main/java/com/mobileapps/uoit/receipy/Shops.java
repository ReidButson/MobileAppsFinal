package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Shops extends AppCompatActivity {
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    ArrayList<Store> stores;
    ArrayList<ArrayList<Ingredient>> store = new ArrayList<>();
    ArrayList<Ingredient> price = new ArrayList<>();
    ArrayList<Double> prices = new ArrayList<>();
    ArrayList<Ingredient> removable = new ArrayList<>();
    Button create_store;
    Intent intent;
    Context context = this;
    DatabaseHelper db;
    private static final String TAG = "Shops";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        db = new DatabaseHelper(this);
        stores = db.getStores();
        initUi();
        initStores();
        getIngredient();
        //db.deleteStores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        stores = db.getStores();
        initStores();
        getPrices();
    }


    private void initUi(){
        create_store = findViewById(R.id.new_store_button);
        create_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_create_store = packageIngredients();
                startActivity(to_create_store);
            }
        });
    }

    private void getIngredient(){
        intent = getIntent();
        int total = intent.getIntExtra("total", 0);
        Log.d(TAG, "getIngredient: " + total);
        for(int i = 0; i < total; i++){
            Ingredient ingredient = (Ingredient) intent.getSerializableExtra("Ingredients"+i);
            ingredients.add(ingredient);
            Log.d(TAG, "getIngredient: " + ingredient.getName());
        }


    }

    private void getPrices(){
        ArrayList<Double> amounts = new ArrayList<>();
        Double sPrice, sAmount, totalTemp = 0.00;
        boolean found = false;
        for (ArrayList<Ingredient> i : store){
            for (int counter = 0; counter<i.size();counter++){
                for(int inner = 0; inner<ingredients.size();inner++){
                    if(ingredients.get(inner).getName().equals(i.get(counter).getName())!= true){
                        found = true;
                    }
                }
                if (found == false){
                    i.remove(counter);
                }
                found = false;
            }
        }

        for(Ingredient y : ingredients){
            amounts.add(y.getAmount());
            Log.d(TAG, "getPrices: "+ y.getAmount());
        }

        for(ArrayList<Ingredient> s : store){
            int counter = 0;
            for(Ingredient i:s){
                sPrice = i.getPrice();
                sAmount = i.getAmount();
                totalTemp += sPrice/sAmount * amounts.get(counter);
                counter ++;
            }
            Log.d(TAG, "getPrices: " + totalTemp);
            prices.add(totalTemp);
            totalTemp = 0.0;
        }

        initStoreView();
    }

    private Intent packageIngredients(){
        Intent parcel = new Intent(this, ShoppingList.class);
        int x = 0;
        for(Ingredient i: ingredients) {
            parcel.putExtra("Ingredients" + x, i);
            x++;
        }
        parcel.putExtra("total", x);
        return parcel;
    }

    private void initStoreView(){
        RecyclerView recyclerView = findViewById(R.id.store_view);
        StoreAdapter adapter = new StoreAdapter(this, stores, prices);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initStores(){
        for(Store s: stores) {
            price = db.getStoreIngredients(s);
            store.add(price);
        }
    }
}
