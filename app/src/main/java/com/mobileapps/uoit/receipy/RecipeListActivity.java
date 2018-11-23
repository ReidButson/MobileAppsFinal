package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    private LinearLayout recipe_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipe_holder = findViewById(R.id.recipe_holder);

        deleteChecked();
        addRecipeConfigure();
    }

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

    void addRecipeConfigure(){
        Button recipe_btn = findViewById(R.id.add_recipe_btn);

        recipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_recipe = new Intent(RecipeListActivity.this, CreateRecipeActivity.class);
                startActivityForResult(create_recipe, 1);
            }
        });
    }
}
