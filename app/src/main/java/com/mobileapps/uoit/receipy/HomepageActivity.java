package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomepageActivity extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Create the database helper
        db = new DatabaseHelper(this);
    }

    public void recipeButton(View v) {
        Intent to_recipe = new Intent(this, RecipeListActivity.class);
        startActivity(to_recipe);
    }

    public void shoppingButton (View v) {
    }
}
