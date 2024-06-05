package com.example.dbms.cart;

import android.content.Context;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;
import com.example.dbms.pages.CartFragment;

import java.util.ArrayList;

public class cartItem_adapter extends RecyclerView.Adapter<cartItem_viewholder> {
    private Context con;
    private ArrayList<cart_item> items;
    private ArrayList<String> targetItems;
    private CartFragment fragment;

    public cartItem_adapter(Context con, CartFragment fragment){
        this.con = con;
        this.fragment = fragment;
        this.items = fragment.getItems();
        this.targetItems = fragment.getTargetItems();
    }
    @NonNull
    @Override
    public cartItem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(con).inflate(R.layout.cart_item,parent,false);
        return new cartItem_viewholder(itemview, this);
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

    public CartFragment getFragment() {
        return fragment;
    }
}
