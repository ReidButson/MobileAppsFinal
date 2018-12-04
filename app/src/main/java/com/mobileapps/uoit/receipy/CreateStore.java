package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CreateStore extends AppCompatActivity {
    Button backBtn, doneBtn;
    EditText storeText;
    RecyclerView ingredientView;
    DatabaseHelper db;
    ArrayList<Double> price = new ArrayList<>();
    ArrayList<Double> qty = new ArrayList<>();
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    Intent intent;
    private static final String TAG = "CreateStore";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        db = new DatabaseHelper(this);
        initUi();
        getIngredientsFromIntent();
        initRecycler();
    }

    private void initUi(){
        doneBtn = findViewById(R.id.create_store_button);
        backBtn = findViewById(R.id.return_button);
        storeText = findViewById(R.id.store_name_field);
        ingredientView = findViewById(R.id.ingredient_price_view);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = storeText.getText().toString();
                Store store = new Store(name, "home");
                int x = (int) db.insertStore(store);
                store.setId(x);
                int y = 0;
                for(Ingredient i : ingredients){
                    Log.d(TAG, "onClick: "+ y);
                    db.insertStoreIngredient(store, i);
                    y++;
                }
                finish();
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecycler(){
        ingredientView = findViewById(R.id.ingredient_price_view);
        StoreIngredientAdaptor adaptor = new StoreIngredientAdaptor(this, ingredients);
        ingredientView.setAdapter(adaptor);
        ingredientView.setLayoutManager(new LinearLayoutManager(this));
    }

    /** Get the ingredient list from the extra.
     *
     */
    private void getIngredientsFromIntent() {
        intent = getIntent();
        ingredients = intent.getParcelableArrayListExtra("INGREDIENTS");
    }
}
