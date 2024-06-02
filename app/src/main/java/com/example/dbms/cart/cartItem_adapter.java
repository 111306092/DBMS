package com.example.dbms.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

import java.util.ArrayList;

public class cartItem_adapter extends RecyclerView.Adapter<cartItem_viewholder> {
    private Context con;
    private ArrayList<cart_item> items;
    private ArrayList<String> targetItems;

    public cartItem_adapter(Context con, ArrayList<cart_item> items, ArrayList<String> targetItems){
        this.con = con;
        this.items = items;
        this.targetItems = targetItems;
    }
    @NonNull
    @Override
    public cartItem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(con).inflate(R.layout.cart_item,parent,false);
        return new cartItem_viewholder(itemview, targetItems, this);
    }

    @Override
    public void onBindViewHolder(@NonNull cartItem_viewholder holder, int position) {
        holder.getItemname().setText(items.get(position).getItemname());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void changeItems(ArrayList<String> targetItems) {
        items = new ArrayList<>();

        for (String s: targetItems) {
            items.add(new cart_item(s));
        }
    }
}
