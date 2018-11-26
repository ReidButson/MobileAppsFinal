package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewRecipe extends Activity {
    RecyclerView recyclerView;
    Button confirmBtn, deleteBtn, addIngredientBtn;
    TextView recipeText;
    DatabaseHelper db;
    Recipe recipe;
    ArrayList<Double> quantity = new ArrayList<Double>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> units = new ArrayList<String>();
    ArrayList<Ingredient> prevIng;
    private static final String TAG = "ViewRecipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        db = new DatabaseHelper(this);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        prevIng = db.getIngredients();
        ArrayList<Ingredient> ingredients;
        ingredients = db.getRecipeIngredients(recipe);
        for (int i =0; i < ingredients.size(); i++){
            names.add(ingredients.get(i).getName());
            quantity.add(ingredients.get(i).getAmount());
            units.add(ingredients.get(i).getUnits());
        }
        initAdapter();
        initUi();
    }



    public void initUi(){
        recipeText = findViewById(R.id.recipe_name_txt2);
        recipeText.setText(recipe.getName());
        addIngredientBtn = findViewById(R.id.add_ingredient_btn);
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names.add("");
                quantity.add(0.0);
                units.add("");
                initAdapter();
            }
        });

        confirmBtn = findViewById(R.id.confirm_button);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                recipe.setRecipe(ingredients);
                db.clearRecipeIngredients(recipe);
                db.insertRecipeIngredients(recipe);
                finish();
            }
        });

        deleteBtn = findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRecipe(recipe);
                finish();
            }
        });
    }

    private void initAdapter(){
        Log.d(TAG, "initAdapter: running");
        recyclerView = findViewById(R.id.ingredient_view);
        IngredientViewAdapter adapter = new IngredientViewAdapter(this, names,units,quantity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Ingredient checkIngredient(String name,double quantity, String units){
        for(Ingredient i: prevIng){
            String ingredientName = i.getName();
            if (ingredientName.equals(name)){
                return i;
            }
        }
        Ingredient ingredient = new Ingredient(1, name, quantity, units);
        db.insertIngredient(ingredient);
        prevIng = db.getIngredients();
        ingredient = prevIng.get(prevIng.size() - 1);
        return ingredient;
    }
}
