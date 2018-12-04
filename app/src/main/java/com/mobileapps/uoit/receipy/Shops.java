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
    static StorePricesAdapter adapter;
    static ArrayList<Store> stores;
    static RecyclerView recyclerView;
    ArrayList<ArrayList<Ingredient>> store = new ArrayList<>();
    ArrayList<Ingredient> price = new ArrayList<>();
    ArrayList<Double> prices = new ArrayList<>();
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

    /** Gets all the ingredients passed from the {{@link Shopping}} activity and places them in the
     * ingredients ArrayList.
     *
     */
    private void getIngredient(){
        intent = getIntent();
        int total = intent.getIntExtra("total", 0);
        Log.d(TAG, "getIngredient total: " + total);
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
     * {{@link com.mobileapps.uoit.receipy.CreateStore} activity
     *
     * @return The intent to be used to run the {{@link CreateStore}} activity.
     */
    private Intent packageIngredients(){
        Intent parcel = new Intent(this, CreateStore.class);
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
