package com.mobileapps.uoit.receipy;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobileapps.uoit.receipy.adapters.IngredientViewAdapter;
import com.mobileapps.uoit.receipy.objects.Ingredient;
import com.mobileapps.uoit.receipy.objects.Recipe;

import java.util.ArrayList;

public class ViewRecipeActivity extends Activity {
    RecyclerView recyclerView;
    Button confirmBtn, deleteBtn, addIngredientBtn;
    TextView recipeText;
    DatabaseHelper db;
    Recipe recipe;
    ArrayList<Double> quantity = new ArrayList<Double>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> units = new ArrayList<String>();
    ArrayList<Ingredient> prevIng;
    private static final String TAG = "ViewRecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        db = new DatabaseHelper(this);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        prevIng = db.getIngredients();
        ArrayList<Ingredient> ingredients;
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
        addIngredientBtn = findViewById(R.id.add_ingredient_btn);
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names.add("");
                quantity.add(null);
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
                    Ingredient ingredient = new Ingredient(checkIngredient(mName, mUnits), mName, mQty, mUnits);
                    ingredients.add(ingredient);
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

    private int checkIngredient(String name, String units){
        for(Ingredient i: prevIng){
            String ingredientName = i.getName();
            if (ingredientName.equals(name)){
                return i.id;
            }
        }
        Ingredient ingredient = new Ingredient(name, units);
        int i = (int) db.insertIngredient(ingredient);
        return i;
    }
}
