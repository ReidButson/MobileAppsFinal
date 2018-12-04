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
    static ArrayList<Ingredient> ingredients = new ArrayList<>();
    DatabaseHelper db;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        db = new DatabaseHelper(this);
        initUI();
        initRecycler();
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
                String name = recipe_text.getText().toString();
                Recipe recipe = db.getRecipeByName(name);

                if (recipe != null) {
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        ingredient.setAmount(0);
                        if (!ingredients.contains(ingredient)) {
                            ingredients.add(ingredient);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(
                                    Shopping.this,
                                    "Ignored some duplicate entries",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Shopping.this,
                            "Recipe not found", Toast.LENGTH_SHORT).show();
                }
                recipe_text.setText("");
            }
        });

        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found = false;
                String name = ingredient_text.getText().toString();
                Ingredient ingredient = db.getIngredientByName(name);
                if (ingredient == null){
                    Toast.makeText(Shopping.this, "Ingredient not found", Toast.LENGTH_SHORT).show();
                }
                else {
                    ingredient.setAmount(0);
                    if (ingredients.contains(ingredient)) {
                        Toast.makeText(
                                    Shopping.this,
                                    "Ignored some duplicate entries",
                                    Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ingredients.add(ingredient);
                        adapter.notifyDataSetChanged();
                    }
                }
                ingredient_text.setText("");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Shops.class);
                intent.putParcelableArrayListExtra("INGREDIENTS", ingredients);
                startActivity(intent);
            }
        });
    }

    private void initRecycler(){
        recyclerView = findViewById(R.id.ingredient_list);
        adapter = new ShoppingIngredientAdapter(this, ingredients);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /** Removes an item from the ingredients array, and updates the adapter.
     *
     * @param position
     */
    public static void removeItem(int position){
        ingredients.remove(position);
        adapter.notifyItemRemoved(position);
    }

}
