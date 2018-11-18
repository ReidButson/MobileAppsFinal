package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        addRecipeConfigure();
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){
        if (request_code == 1){
            final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final LinearLayout recipe_holder = findViewById(R.id.recipe_holder);

            String name = data.getStringExtra("RECIPE_NAME");

            View recipe = inflater.inflate(R.layout.recipe_field, null);

            TextView name_field =  recipe.findViewById(R.id.recipe_name_txt);

            name_field.setText(name);

            recipe_holder.addView(recipe, recipe_holder.getChildCount() - 1);
        }
    }

    void addRecipeConfigure(){
        Button recipe_btn = findViewById(R.id.add_recipe_btn);

        recipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_recipe = new Intent(RecipeList.this, CreateRecipe.class);
                startActivityForResult(create_recipe, 1);
            }
        });
    }
}
