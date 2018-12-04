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

public class StorePricesAdapter extends RecyclerView.Adapter<StorePricesAdapter.ViewHolder>{

    ArrayList<Store> stores;
    ArrayList<Double> prices;
    ArrayList<Ingredient> store_ingredients;
    Context mContext;

    public StorePricesAdapter(Context context, ArrayList<Store> stores, ArrayList<Double> prices, ArrayList<Ingredient> store_ingredients){
        this.prices = prices;
        this.stores = stores;
        this.store_ingredients = store_ingredients;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_item, viewGroup, false);
        StorePricesAdapter.ViewHolder viewHolder = new StorePricesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.store_name.setText(stores.get(i).getName());
        viewHolder.price_text.setText(String.format("%.2f", prices.get(i)));
        viewHolder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (viewHolder.del_btn.getVisibility() == View.GONE){
                    viewHolder.del_btn.setVisibility(View.VISIBLE);
                }
                else{
                    viewHolder.del_btn.setVisibility(View.GONE);
                }
                return false;
            }
        });

        viewHolder.del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbh = new DatabaseHelper(mContext);
                dbh.deleteStore(stores.get(i));
                Shops.removeItem(i);
            }
        });

        viewHolder.shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_shop_list = new Intent(mContext, ShoppingList.class);
                int x = 0;
                for(Ingredient i: store_ingredients) {
                    to_shop_list.putExtra("Ingredients" + x, i);
                    x++;
                }
                to_shop_list.putExtra("store",stores.get(i));
                to_shop_list.putExtra("total", x);
                mContext.startActivity(to_shop_list);
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
        Button shop_btn;
        Button del_btn;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store_name = itemView.findViewById(R.id.store_name_text);
            price_text = itemView.findViewById(R.id.price_text);
            parentLayout = itemView.findViewById(R.id.parent_id);
            shop_btn = itemView.findViewById(R.id.btn_shop);
            del_btn = itemView.findViewById(R.id.btn_delete);

        }
    }
}
