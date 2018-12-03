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
        getIngredient();
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
                    i.setAmount(qty.get(y));
                    i.setPrice(price.get(y));
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
        StoreIngredientAdaptor adaptor = new StoreIngredientAdaptor(this, ingredients, price, qty);
        ingredientView.setAdapter(adaptor);
        ingredientView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getIngredient() {
        intent = getIntent();
        int total = intent.getIntExtra("total", 0);
        Log.d(TAG, "getIngredient: "+total);
        try{
            for (int i = 0; i < total; i++) {
                Ingredient ingredient = (Ingredient) intent.getSerializableExtra("Ingredients" + i);
                Log.d(TAG, "getIngredient: "+ingredient.getName());
                ingredients.add(ingredient);
                price.add(null);
                qty.add(null);
            }
        }catch (NullPointerException e){
            System.out.println("NULL");
        }

    }
}