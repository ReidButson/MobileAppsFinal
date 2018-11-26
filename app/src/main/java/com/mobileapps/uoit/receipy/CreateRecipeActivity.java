package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CreateRecipeActivity extends AppCompatActivity {
    DatabaseHelper db;
    RecyclerView recyclerView;
    ArrayList<Double> quantity = new ArrayList<Double>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> units = new ArrayList<String>();
    Button newIngredientBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        db = new DatabaseHelper(this);
        initButton();
        doneConfigure();
    }

    private void initButton() {
        newIngredientBtn = findViewById(R.id.new_ingredient_button);
        newIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIngredient(v);
            }
        });
    }

    void doneConfigure(){
        Button confirm_btn = findViewById(R.id.save_recipe_btn);
        final EditText recipe_name_entry = findViewById(R.id.recipe_name_entry);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipe_name = recipe_name_entry.getText().toString();
                ArrayList<Ingredient> ingredients = new ArrayList<>();

                for (int i = 0; i < names.size(); i++){
                    Double qty = quantity.get(i);
                    String nom = names.get(i);
                    Ingredient ingredient = new Ingredient(i, nom, qty, units.get(i));
                    ingredients.add(ingredient);
                    db.insertIngredient(ingredient);
                }

                Recipe recipe = new Recipe(1,recipe_name,ingredients);
                db.insertRecipe(recipe);
                db.insertRecipeIngredients(recipe);
                finish();

            }
        });
    }

    private void initAdapter(){
        recyclerView = findViewById(R.id.recyclerView);
        IngredientsAdapter adapter = new IngredientsAdapter(this, names,units,quantity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void newIngredient(View v){
        quantity.add(0.0);
        names.add("");
        units.add("");
        initAdapter();
    }

}
