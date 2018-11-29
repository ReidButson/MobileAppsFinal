package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Shops extends AppCompatActivity {
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    Button create_store;
    Intent intent;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        getIngredient();

    }

    private void initUi(){
        create_store = findViewById(R.id.new_store_button);
        create_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_create_store = new Intent(context, CreateStore.class);
                to_create_store.putExtras(intent);
                startActivity(to_create_store);
            }
        });
    }

    private void getIngredient(){
        intent = getIntent();
        int total = intent.getIntExtra("total", 0);
        for(int i = 0; i < total; i++){
            Ingredient ingredient = (Ingredient) intent.getSerializableExtra("Ingredients"+i);
            ingredients.add(ingredient);
        }
    }


}
