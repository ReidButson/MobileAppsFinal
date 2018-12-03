package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreIngredientAdaptor extends RecyclerView.Adapter<StoreIngredientAdaptor.ViewHolder>{
    private static final String TAG = "IngredientsAdapter";
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Double> price, quantity;
    private Context mContext;

    public StoreIngredientAdaptor(Context context, ArrayList<Ingredient> ingredients, ArrayList<Double> price, ArrayList<Double> quantity) {
        mContext = context;
        mIngredients = ingredients;
        this.price = price;
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_ingredient_view, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.ingredient.setText(mIngredients.get(i).getName());
        viewHolder.priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double cost;
                try{
                    cost = Double.parseDouble(viewHolder.priceText.getText().toString());
                    price.set(i, cost);
                    Log.d(TAG, "afterTextChanged: success" + cost);
                }catch (NumberFormatException e){
                    cost = null;
                    price.set(i, cost);
                    Log.d(TAG, "afterTextChanged: failure");
                }
                price.add(cost);
            }
        });

        viewHolder.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double pricePer;
                try{
                    pricePer = Double.parseDouble(viewHolder.qty.getText().toString());
                    quantity.set(i, pricePer);
                    Log.d(TAG, "afterTextChanged: success" + pricePer);
                }catch (NumberFormatException e){
                    pricePer = null;
                    quantity.set(i, pricePer);
                    Log.d(TAG, "afterTextChanged: failure");
                }
                quantity.add(pricePer);
            }
        });

    }

    @Override
    public int getItemCount() {
        try{
            return mIngredients.size();
        }catch (NullPointerException e){
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText priceText, qty;
        TextView ingredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient_name);
            priceText = itemView.findViewById(R.id.price_field);
            qty = itemView.findViewById(R.id.quantity_field);
        }
    }
}

