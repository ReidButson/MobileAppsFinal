package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class LogPricesActivity extends AppCompatActivity {

    ArrayList<Ingredient> ingredients;
    int store_id;
    String store_name;
    RecyclerView recycler;
    StoreIngredientAdaptor adaptor;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_prices);
        // Setup the database
        db = new DatabaseHelper(this);
        // Get everything from the intent
        setupIntent();
        setupRecycler();
    }

    public void setupIntent() {
        Intent intent = getIntent();
        ingredients = intent.getParcelableArrayListExtra("INGREDIENTS");
        store_id = intent.getIntExtra("ID", -1);
        store_name = intent.getStringExtra("NAME");
    }

    public void setupRecycler() {
        recycler = findViewById(R.id.price_recycler);
        adaptor = new StoreIngredientAdaptor(this, ingredients);
        recycler.setAdapter(adaptor);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void confirmLog(View v) {
        // Write values to the database
        db.writePrices(store_id, ingredients);
        // Finish the activity
        finish();
    }
}
