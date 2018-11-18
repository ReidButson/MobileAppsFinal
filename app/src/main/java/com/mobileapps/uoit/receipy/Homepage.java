package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recipesConifgure();
    }

    void recipesConifgure(){
        Button recipes_btn = findViewById(R.id.recipe_list_btn);

        recipes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRecipes = new Intent(Homepage.this, RecipeList.class);
                startActivity(toRecipes);
            }
        });
    }
}
