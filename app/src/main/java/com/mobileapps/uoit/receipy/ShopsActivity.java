package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mobileapps.uoit.receipy.adapters.StorePricesAdapter;
import com.mobileapps.uoit.receipy.objects.Ingredient;
import com.mobileapps.uoit.receipy.objects.Recipe;
import com.mobileapps.uoit.receipy.objects.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ShopsActivity extends AppCompatActivity {
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    static StorePricesAdapter adapter;
    static ArrayList<Store> stores;
    static RecyclerView recyclerView;
    ArrayList<ArrayList<Ingredient>> store = new ArrayList<>();
    ArrayList<Ingredient> price = new ArrayList<>();
    ArrayList<Double> prices = new ArrayList<>();
    Button create_store;
    Intent intent;
    DatabaseHelper db;
    private static final String TAG = "ShopsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        db = new DatabaseHelper(this);
        stores = db.getStores();
        initUi();
        initStores();
        // Receive all ingredients from the intent here
        getIntentExtras();

        //db.deleteStores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        stores = db.getStores();
        store.clear();
        prices.clear();
        initStores();
        getPrices();
    }

    /** Initializes all the ui components on the activity.
     *
     */
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

    /** Gets all the ingredients passed from the {{@link ShoppingActivity}} activity and places them in the
     * ingredients ArrayList.
     *
     */
    private void getIntentExtras(){
        intent = getIntent();
        // Get the ArrayList of items that need to be grabbed
        ArrayList<Ingredient> received_ingredients = intent.getParcelableArrayListExtra("INGREDIENTS");
        ArrayList<Recipe> received_recipes = intent.getParcelableArrayListExtra("RECIPES");
        // Loop through them all separately so they are only shown once
        HashMap<Integer, Ingredient> ingredient_hash = new LinkedHashMap<>();
        if (received_ingredients != null) {
            for (Ingredient received_ingredient: received_ingredients) {
                if (!ingredient_hash.containsKey(received_ingredient.getId())) {
                    ingredient_hash.put(received_ingredient.getId(), received_ingredient);
                }
            }
        }
        if (received_recipes != null) {
            for (Recipe received_recipe : received_recipes) {
                if (received_recipe.getIngredients() != null) {
                    for (Ingredient received_ingredient : received_recipe.getIngredients()) {
                        if (!ingredient_hash.containsKey(received_ingredient.getId())) {
                            ingredient_hash.put(received_ingredient.getId(), received_ingredient);
                        }
                    }
                }
            }
        }
        // Add all the hash map values into the ingredients array
        ingredients.addAll(ingredient_hash.values());
        System.out.println("received extras");
    }

    private void getPrices(){
        ArrayList<Double> amounts = new ArrayList<>();
        Double sPrice, sAmount, totalTemp = 0.00;
        boolean found = false;
        for (ArrayList<Ingredient> i : store){
            for (int counter = 0; counter<i.size(); counter++){
                for(int inner = 0; inner<ingredients.size();inner++){
                    if(ingredients.get(inner).getName().equals(i.get(counter).getName())){
                        found = true;
                    }
                }
                if (!found){
                    i.remove(counter);
                }
                found = false;
            }
        }

        for(Ingredient y : ingredients){
            amounts.add(y.getAmount());
            //Log.d(TAG, "getAmount: "+ y.getAmount());
        }

/*        for(ArrayList<Ingredient> s : store){
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
 */
        // Loop through each store, and track the price it would cost to shop at that store
        for(ArrayList<Ingredient> s : store){
            for(int counter = 0; counter < s.size(); counter++){
                sPrice = s.get(counter).getPrice();
                sAmount = s.get(counter).getAmount();
                totalTemp += sPrice/sAmount * amounts.get(counter);
            }
            Log.d(TAG, "getPrices: " + totalTemp);
            prices.add(totalTemp);
            totalTemp = 0.0;
        }

        initStoreView();
    }

    /** Creates an intent for moving ingredient objects to the
     * {{@link CreateStoreActivity} activity
     *
     * @return The intent to be used to run the {{@link CreateStoreActivity}} activity.
     */
    private Intent packageIngredients(){
        Intent parcel = new Intent(this, CreateStoreActivity.class);
        int x = 0;
        for(Ingredient i: ingredients) {
            parcel.putExtra("Ingredients" + x, i);
            x++;
        }
        parcel.putExtra("total", x);
        return parcel;
    }

    private void initStoreView(){
        recyclerView = findViewById(R.id.store_view);
        adapter = new StorePricesAdapter(this, stores, prices, ingredients);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initStores(){
        for(Store s: stores) {
            price = db.getStoreIngredients(s);
            store.add(price);
        }
    }

    public static void removeItem(int position) {
        stores.remove(position);
        recyclerView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, stores.size());
        adapter.notifyDataSetChanged();
    }
}
