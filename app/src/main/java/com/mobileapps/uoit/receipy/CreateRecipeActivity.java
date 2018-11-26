package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CreateRecipeActivity extends AppCompatActivity {
    DatabaseHelper db;
    RecyclerView recyclerView;
    ArrayList<Double> quantity = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> units = new ArrayList<>();
    ArrayList<Ingredient> prevIng = new ArrayList<>();
    Button newIngredientBtn;
    private static final String TAG = "CreateRecipeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        db = new DatabaseHelper(this);
        try{
            prevIng = db.getIngredients();
        }catch (RuntimeException e){
            System.out.println("empty database");
        }

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
                    Double mQty = quantity.get(i);
                    String mName = names.get(i);
                    String mUnits = units.get(i);
                    Ingredient ingredient = checkIngredient(mName, mQty, mUnits);
                    ingredients.add(ingredient);
                }

                for(Ingredient i: ingredients){
                    int x = 0;
                    i.setAmount(quantity.get(x));
                    x++;
                }

                Recipe recipe = new Recipe(1,recipe_name,ingredients);
                db.insertRecipe(recipe);
                ArrayList<Recipe> recipes = db.getRecipes();
                int x = recipes.get(recipes.size() - 1).getId();
                recipe = new Recipe(x,recipe_name,ingredients);
                db.insertRecipeIngredients(recipe);
                finish();
            }
        });
    }

    private Ingredient checkIngredient(String name,double quantity, String units){
        for(Ingredient i: prevIng){
            String ingredientName = i.getName();
            if (ingredientName.equals(name)){
                Log.d(TAG, "checkIngredient: Duplicate food thing");
                return i;
            }
        }
        Ingredient ingredient = new Ingredient(1, name, quantity, units);
        db.insertIngredient(ingredient);
        prevIng = db.getIngredients();
        ingredient = prevIng.get(prevIng.size() - 1);
        return ingredient;
    }

    private void initAdapter(){
        recyclerView = findViewById(R.id.recyclerView);
        IngredientsAdapter adapter = new IngredientsAdapter(this, names, units, quantity);
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
