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

import java.util.ArrayList;

public class ViewRecipe extends Activity {
    RecyclerView recyclerView;
    Button confirmBtn, deleteBtn;
    TextView recipeText;
    DatabaseHelper db;
    Recipe recipe;
    ArrayList<Double> quantity = new ArrayList<Double>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> units = new ArrayList<String>();
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    private static final String TAG = "ViewRecipe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        db = new DatabaseHelper(this);
        db.getRecipes();
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        //recipe = db.getRecipeIngredients(recipe); <--- Database has no idea what this recipe table is
        ingredients = recipe.getIngredients();
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
        confirmBtn = findViewById(R.id.confirm_button);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteBtn = findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
