package com.mobileapps.uoit.receipy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobileapps.uoit.receipy.adapters.IngredientsAdapter;
import com.mobileapps.uoit.receipy.objects.Ingredient;
import com.mobileapps.uoit.receipy.objects.Recipe;

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
                    Ingredient ingredient = new Ingredient(checkIngredient(mName, mUnits),mName, mQty, mUnits);
                    ingredients.add(ingredient);
                }

                Recipe recipe = new Recipe(1,recipe_name,ingredients);
                int x = (int) db.insertRecipe(recipe);
                recipe = new Recipe(x, recipe_name, ingredients);
                db.insertRecipeIngredients(recipe);

                finish();
            }
        });
    }

    private int checkIngredient(String name, String units){
        for(Ingredient i: prevIng){
            String ingredientName = i.getName();
            if (ingredientName.equals(name)){
                Log.d(TAG, "checkIngredient: Duplicate food thing");
                return i.id;
            }
        }
        Ingredient ingredient = new Ingredient(name, units);
        int  i = (int) db.insertIngredient(ingredient);
        prevIng = db.getIngredients();
        return i;
    }

    private void initAdapter(){
        recyclerView = findViewById(R.id.recyclerView);
        IngredientsAdapter adapter = new IngredientsAdapter(this, names, units, quantity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void newIngredient(View v){
        quantity.add(null);
        names.add("");
        units.add("");
        initAdapter();
    }

}
