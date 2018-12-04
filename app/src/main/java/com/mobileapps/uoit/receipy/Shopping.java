package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Shopping extends AppCompatActivity {
    ImageButton add_recipe;
    ImageButton add_ingredient;
    static RecyclerView recyclerView;
    static ShoppingIngredientAdapter adapter;
    Button confirm;
    EditText recipe_text;
    EditText ingredient_text;
    ArrayList<Ingredient> ingredients;
    ArrayList<Recipe> recipes;
    static ArrayList<Ingredient> ingredient_list = new ArrayList<>();
    DatabaseHelper db;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        db = new DatabaseHelper(this);
        initUI();
        initRecycler();
        // The ArrayLists of added recipes and ingredients to pass to the Shops intent
        ingredients = new ArrayList<>();
        recipes = new ArrayList<>();
        // Used when debugging to clear the database.
        // db.clearShit();
    }

    private void initUI(){
        add_recipe = findViewById(R.id.add_recipe_shopping_btn);
        add_ingredient = findViewById(R.id.add_ingredient_shopping_btn);
        confirm = findViewById(R.id.go_shopping_btn);
        recipe_text = findViewById(R.id.recipe_name_text);
        ingredient_text = findViewById(R.id.ingredient_name_text);

        add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Search for the recipe
                Recipe searched_recipe = db.getRecipeByName(recipe_text.getText().toString());
                // If the recipe isn't found, show an error message
                if (searched_recipe == null) {
                    Toast.makeText(Shopping.this, "Recipe not found", Toast.LENGTH_SHORT).show();
                }
                // Add it to the list if it was found
                else {
                    recipes.add(searched_recipe);
                    recipe_text.setText("");
                    ingredient_list.addAll(searched_recipe.getIngredients());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Search for the ingredient
                Ingredient searched_ingredient = db.getIngredientByName(
                        ingredient_text.getText().toString());
                // If the ingredient isn't found, show an error message
                if (searched_ingredient == null) {
                    Toast.makeText(Shopping.this, "Ingredient not found", Toast.LENGTH_SHORT).show();
                }
                // Add it to the list if it was found
                else {
                    ingredients.add(searched_ingredient);
                    ingredient_text.setText("");
                    ingredient_list.add(searched_ingredient);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent to go to the Shops activity
                Intent intent = new Intent(context, Shops.class);
                // Pass the ingredients and recipes
                Bundle bundle = new Bundle();
                bundle.putSerializable("INGREDIENTS", ingredients);
                bundle.putSerializable("RECIPES", recipes);
                intent.putExtras(bundle);
            }
        });

    }

    private void initRecycler(){
        recyclerView = findViewById(R.id.ingredient_list);
        adapter = new ShoppingIngredientAdapter(this, ingredient_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static void removeItem(int position){
        ingredient_list.remove(position);
        recyclerView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, ingredient_list.size());
        adapter.notifyDataSetChanged();
    }

}
