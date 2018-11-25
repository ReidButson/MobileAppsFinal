package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private static final String TAG = "RecipeAdapter";
    private ArrayList<Recipe> mRecipeNames;
    //private ArrayList<String> images = new ArrayList<>();
    private Context mContext;


    public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
        this.mRecipeNames = recipes;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called"); //debugging
        viewHolder.recipeNames.setText(mRecipeNames.get(i).getName());
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on: " + mRecipeNames.get(i));
                Toast.makeText(mContext, mRecipeNames.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on: " + mRecipeNames.get(i));
                Intent intent = new Intent(mContext, ViewRecipe.class);
                intent.putExtra("recipe", mRecipeNames.get(i));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipeNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView recipeNames;
        RelativeLayout parentLayout;
        Button editButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editButton = itemView.findViewById(R.id.view_recipe_btn);
            recipeNames = itemView.findViewById(R.id.recipe_name_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
