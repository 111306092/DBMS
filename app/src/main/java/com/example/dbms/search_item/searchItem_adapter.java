package com.example.dbms.search_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

import java.util.ArrayList;

public class searchItem_adapter extends RecyclerView.Adapter<searchItem_viewholder> {

    private Context con;
    private ArrayList<search_item> items;

    public searchItem_adapter(Context con, ArrayList<search_item> items){
        this.con = con;
        this.items = items;
    }

    @NonNull
    @Override
    public searchItem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(con).inflate(R.layout.search_item,parent,false);
        return new searchItem_viewholder(itemview);
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
