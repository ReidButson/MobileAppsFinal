package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {
    DatabaseHelper db;
    private LinearLayout recipe_holder;
    private static final String TAG = "RecipeList";
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipe_holder = findViewById(R.id.recipe_holder);
        db = new DatabaseHelper(this);

        addRecipeConfigure();
        initDb();
    }
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        initDb();
        addRecipeConfigure();
    }

    private void initDb (){
        try{
            recipes = db.getRecipes();
            ingredients = db.getIngredients();
        }catch (RuntimeException e){
            System.out.println("empty database");
        }


        initRecyclerView();
    }

    void addRecipeConfigure(){
        Button recipe_btn = findViewById(R.id.add_recipe_btn);

        recipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent potato = new Intent(RecipeListActivity.this, CreateRecipeActivity.class);
                startActivity(potato);
            }
        });
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: INITIATES");
        RecyclerView recyclerView = findViewById(R.id.recipe_list);
        RecipeAdapter adapter = new RecipeAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /***
     * Reid's code not sure if he wants to keep this stuff and like repurpose it so ill just leave it commented out
     */
    /*
    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){
        if (request_code == 1){
            if(result_code == RESULT_CANCELED){
                return;
            }
            final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            String name = data.getStringExtra("RECIPE_NAME");

            final View recipe = inflater.inflate(R.layout.recipe_ingredient_item, null);

            recipe.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    revealChecks();
                    return false;
                }
            });

            TextView name_field =  recipe.findViewById(R.id.recipe_name_txt);

            name_field.setText(name);

            recipe_holder.addView(recipe, recipe_holder.getChildCount() - 1);
        }
    }
    */
    /*
    public void deleteChecked(){
        final FloatingActionButton delete_flt_btn = findViewById(R.id.floatingActionButton);

        delete_flt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<View> blacklisted = new ArrayList<>();
                for (int i = 0; i < recipe_holder.getChildCount(); i++){
                    View recipe_panel = recipe_holder.getChildAt(i);
                    CheckBox check = recipe_panel.findViewById(R.id.delete_chkbx);
                    if (check.isChecked()){
                        blacklisted.add(recipe_panel);
                    }
                }
                for(View x : blacklisted){
                    delete_recipe(x);
                }
                hideChecks();
            }
        });
    }

    public void revealChecks() {
        findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
        for (int i = 0; i < recipe_holder.getChildCount(); i++) {
            View recipe_panel = recipe_holder.getChildAt(i);
            CheckBox check = recipe_panel.findViewById(R.id.delete_chkbx);
            check.setVisibility(View.VISIBLE);
        }
    }

    public void hideChecks() {
        findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
        for (int i = 0; i < recipe_holder.getChildCount(); i++) {
            View recipe_panel = recipe_holder.getChildAt(i);
            CheckBox check = recipe_panel.findViewById(R.id.delete_chkbx);
            check.setVisibility(View.INVISIBLE);
        }
        findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);
    }

    public void delete_recipe(View v){
        recipe_holder.removeView(v);
    }
    */
}
