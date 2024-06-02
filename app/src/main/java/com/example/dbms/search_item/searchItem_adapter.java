package com.example.dbms.search_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class searchItem_adapter extends RecyclerView.Adapter<searchItem_viewholder> {

    private Context con;
    private ArrayList<search_item> items;
    private ArrayList<String> targetItems;

    public searchItem_adapter(Context con, ArrayList<search_item> items, ArrayList<String> targetItems){
        this.con = con;
        this.items = items;
        this.targetItems = targetItems;
    }

    @NonNull
    @Override
    public searchItem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(con).inflate(R.layout.search_item,parent,false);
        return new searchItem_viewholder(itemview, targetItems);
    }

    @Override
    public void onBindViewHolder(@NonNull searchItem_viewholder holder, int position) {
        holder.getItemname().setText(items.get(position).getItemname());
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
