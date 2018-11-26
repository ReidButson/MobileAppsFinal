package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class IngredientViewAdapter extends RecyclerView.Adapter<IngredientViewAdapter.ViewHolder> {
    private static final String TAG = "IngredientViewAdapter";
    private ArrayList<String> mIngredients;
    private ArrayList<Double> mQuantity;
    private ArrayList<String> mUnits;
    private Context mContext;

    public IngredientViewAdapter(Context context, ArrayList<String> ingredients, ArrayList<String> units, ArrayList<Double> amount) {
        mContext = context;
        mIngredients = ingredients;
        mQuantity = amount;
        mUnits = units;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_field, viewGroup, false);
        IngredientViewAdapter.ViewHolder holder = new IngredientViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: INITIATED");
        viewHolder.ingredient.setText(mIngredients.get(i));
        Log.d(TAG, "onBindViewHolder: " + mIngredients.get(i));
        viewHolder.quantity.setText(Double.toString(mQuantity.get(i)));
        viewHolder.units.setText(mUnits.get(i));

        viewHolder.ingredient.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String name = viewHolder.ingredient.getText().toString();
                    mIngredients.set(i, name);
                }
            }
        });

        viewHolder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try {
                        Double qty = Double.parseDouble(viewHolder.quantity.getText().toString());
                        mQuantity.set(i, qty);
                    }
                    catch (NumberFormatException e) {
                        mQuantity.set(i, 0.0);
                    }
                }
            }
        });

        viewHolder.units.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String unit = viewHolder.units.getText().toString();
                    mUnits.set(i, unit);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText ingredient;
        EditText quantity;
        EditText units;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient_entry);
            quantity = itemView.findViewById(R.id.quantity_entry);
            units = itemView.findViewById(R.id.units_entry);
        }
    }
}
