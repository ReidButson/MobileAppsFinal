package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>{

    ArrayList<Store> stores;
    Context context;

    public StoreAdapter(Context context, ArrayList<Store> stores) {
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.store_card, viewGroup, false);
        ViewHolder'.'
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Set up the edit button
        viewHolder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO launch intent to edit the store
            }
        });

        // Set the text to be the name of the store
        viewHolder.store_lbl.setText(stores.get(i).getName());

        // Set up the select button (enables logging prices into the database under the store)
        viewHolder.select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO launch intent to log prices in database
            }
        });
    }


    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // All fields in a card
        Button edit_button;
        TextView store_lbl;
        Button select_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Button edit_button = itemView.findViewById(R.id.store_edit_delete_btn);
            TextView store_lbl = itemView.findViewById(R.id.store_name);
            Button select_button = itemView.findViewById(R.id.select_store_button);
        }
    }
}
