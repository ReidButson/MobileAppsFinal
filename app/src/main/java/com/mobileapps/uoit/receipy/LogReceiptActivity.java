package com.mobileapps.uoit.receipy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class LogReceiptActivity extends AppCompatActivity {

    // The database helper for the class
    DatabaseHelper db;
    // The list of known stores to be retrieved from the database
    ArrayList<Store> stores;
    // The recycler view for the store cards
    RecyclerView recycler;
    // The adapter for the recycler view
    StoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_receipt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        stores = db.getStores();
        adapter = new StoreAdapter(this, stores);
        recycler = findViewById(R.id.store_recycler);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
