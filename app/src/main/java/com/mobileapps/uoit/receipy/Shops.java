package com.mobileapps.uoit.receipy;

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
    static ArrayList<Store> stores;

    // The recycler view for the class
    RecyclerView recycler;
    static StoreAdapter adapter;

    Button create_store;
    Intent intent;
    DatabaseHelper db;
    private static final String TAG = "Shops";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        db = new DatabaseHelper(this);
        // Get all the stores from the database
        stores = db.getStores();
        initUi();
        // Get the ingredients from the database
        getIngredientsFromIntent();
        //db.deleteStores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        stores = db.getStores();
        getPrices();
        initRecycler();
    }


    private void initUi(){
        create_store = findViewById(R.id.new_store_button);
        create_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_create_store = new Intent(Shops.this, CreateStore.class);
                to_create_store.putParcelableArrayListExtra("INGREDIENTS", ingredients);
                startActivity(to_create_store);
            }
        });
    }

    /** Gets the ingredient objects from the passed intent
     *
     */
    private void getIngredientsFromIntent(){
        intent = getIntent();
        ingredients = intent.getParcelableArrayListExtra("INGREDIENTS");
    }

    /** Gets the prices for the entire ingredient list at each store and saves it into the store
     * ArrayList.  Initializes the recycler afterwards.
     *
     */
    private void getPrices(){
        // Loop through all the stores
        for (int i = 0; i < stores.size(); i++) {
            // Get the price as a store
            Store new_store = db.getPriceAtStore(stores.get(i), ingredients);
            // Reset the store to the new store
            stores.set(i, new_store);
        }

        // Initialize the recycler
        initRecycler();
    }

    /** Initializes the recycler for the activity.
     *
     */
    private void initRecycler(){
        recycler = findViewById(R.id.store_view);
        adapter = new StoreAdapter(this, stores);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public static void removeItem(int position){
        stores.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
