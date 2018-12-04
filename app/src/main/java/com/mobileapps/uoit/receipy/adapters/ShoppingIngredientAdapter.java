package com.mobileapps.uoit.receipy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapps.uoit.receipy.R;
import com.mobileapps.uoit.receipy.ShoppingActivity;
import com.mobileapps.uoit.receipy.objects.Ingredient;

import java.util.ArrayList;

public class ShoppingIngredientAdapter extends RecyclerView.Adapter<ShoppingIngredientAdapter.ViewHolder>{
    private static final String TAG = "IngredientsAdapter";
    private ArrayList<Ingredient> mIngredients;
    private Context mContext;

    public ShoppingIngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_list_view, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.ingredient.setText(mIngredients.get(i).getName());
        viewHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Deleting " + mIngredients.get(i).getName(), Toast.LENGTH_SHORT).show();
                ShoppingActivity.removeItem(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button delete_button;
        TextView ingredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient_name);
            delete_button = itemView.findViewById(R.id.delete_ingredient_button);
        }
    }
}

