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
        ingredients = db.getIngredients();
        recipes = db.getRecipes();
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
                boolean found = false;
                String name = recipe_text.getText().toString();
                for(Recipe r: recipes){
                    if (r.name.equals(name)){
                        getIngredient(r);
                        found = true;
                    }
                }
                if (found == false){
                    Toast.makeText(Shopping.this, "Recipe not found", Toast.LENGTH_SHORT).show();
                }
                recipe_text.setText("");
            }
        });

        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found = false;
                String name = ingredient_text.getText().toString();
                for(Ingredient i: ingredients){
                    if(name.equals(i.name)){
                        ingredient_list.add(i);
                        found = true;
                    }
                }
                if (found != true){
                    Toast.makeText(Shopping.this, "Ingredient not found", Toast.LENGTH_SHORT).show();
                }
                ingredient_text.setText("");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Shops.class);
                int x = 0;
                for(Ingredient i: ingredient_list){
                    intent.putExtra("Ingredients" + x, i);
                    x++;
                }
                intent.putExtra("total", x);
                startActivity(intent);
            }
        });

    }

    private void getIngredient(Recipe recipe){
        for(Ingredient i: db.getRecipeIngredients(recipe)){
            ingredient_list.add(i);
        }
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
