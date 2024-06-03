package com.example.dbms.search_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;
import com.example.dbms.pages.SearchFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class searchItem_adapter extends RecyclerView.Adapter<searchItem_viewholder> {

    private Context con;
    private ArrayList<search_item> items;
    private SearchFragment fragment;

    public searchItem_adapter(Context con, ArrayList<search_item> items, SearchFragment fragment){
        this.con = con;
        this.items = items;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public searchItem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(con).inflate(R.layout.search_item,parent,false);
        return new searchItem_viewholder(itemview, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull searchItem_viewholder holder, int position) {
        holder.setProductInfo(items.get(position).getItemname());
        String[] temp = items.get(position).getItemname().split("/AND/");

        holder.getItemname().setText(temp[0]);
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
