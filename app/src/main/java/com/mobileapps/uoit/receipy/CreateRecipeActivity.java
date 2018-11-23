package com.mobileapps.uoit.receipy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        doneConfigure();
    }

    void doneConfigure(){
        Button confirm_btn = findViewById(R.id.save_recipe_btn);
        final EditText recipe_name_entry = findViewById(R.id.recipe_name_entry);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipe_name = recipe_name_entry.getText().toString();
                Intent intent = getIntent();

                intent.putExtra("RECIPE_NAME", recipe_name);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }
}
