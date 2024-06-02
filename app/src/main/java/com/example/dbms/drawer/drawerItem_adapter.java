package com.example.dbms.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

import java.util.ArrayList;

public class drawerItem_adapter extends RecyclerView.Adapter<drawerItem_viewholder>{
    private Context con;
    private ArrayList<drawer_item> items;

    public drawerItem_adapter(Context con, ArrayList<drawer_item> items){
        this.con = con;
        this.items = items;
    }
    @NonNull
    @Override
    public drawerItem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(con).inflate(R.layout.drawer_item,parent,false);
        return new drawerItem_viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull drawerItem_viewholder holder, int position) {
        holder.getItemname().setText(items.get(position).getItemname());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void changeItems(ArrayList<drawer_item> items) {
        this.items = items;
    }
}
