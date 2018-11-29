package com.mobileapps.uoit.receipy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateStore extends AppCompatActivity {
    Button backBtn, doneBtn;
    EditText storeText;
    RecyclerView ingredientView;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        db = new DatabaseHelper(this);
        initUi();
    }

    private void initUi(){
        doneBtn = findViewById(R.id.create_store_button);
        backBtn = findViewById(R.id.return_button);
        storeText = findViewById(R.id.store_name_field);
        ingredientView = findViewById(R.id.ingredient_price_view);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = storeText.getText().toString();
                Store store = new Store(name, null);
                int x = (int) db.insertStore(store);
                store.setId(x);

                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
