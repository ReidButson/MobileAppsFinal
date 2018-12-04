package com.mobileapps.uoit.receipy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static com.mobileapps.uoit.receipy.Shopping.ingredients;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>{

    ArrayList<Store> stores;
    Context mContext;

    public StoreAdapter(Context context, ArrayList<Store> stores){
        this.stores = stores;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_item, viewGroup, false);
        StoreAdapter.ViewHolder viewHolder = new StoreAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Store current_store = stores.get(i);
        viewHolder.store_name.setText(current_store.getName());
        viewHolder.price_text.setText(String.format(Locale.getDefault(),
                "$%.2f, %d prices not found", current_store.getPrice(),
                current_store.getItems_not_found()));
        viewHolder.go_shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to go to the create shop activity
                Intent intent = new Intent(mContext, LogPricesActivity.class);
                intent.putParcelableArrayListExtra("INGREDIENTS", ingredients);
                intent.putExtra("ID", current_store.getId());
                intent.putExtra("NAME", current_store.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView price_text;
        TextView store_name;
        Button go_shopping_btn;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store_name = itemView.findViewById(R.id.store_name_text);
            price_text = itemView.findViewById(R.id.price_text);
            go_shopping_btn = itemView.findViewById(R.id.go_shopping_btn);
            parentLayout = itemView.findViewById(R.id.parent_id);
        }
    }
}
