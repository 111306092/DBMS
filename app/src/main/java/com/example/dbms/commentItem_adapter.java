package com.example.dbms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class commentItem_adapter extends RecyclerView.Adapter<commentItem_viewholder> {

    private Context con;
    private ArrayList<comment_item> items;

    public commentItem_adapter(Context con, ArrayList<comment_item> items){
        this.con = con;
        this.items = items;
    }
    @NonNull
    @Override
    public commentItem_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(con).inflate(R.layout.comment_item,parent,false);
        return new commentItem_viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull commentItem_viewholder holder, int position) {
        holder.getItemname().setText(items.get(position).getItemname());
        holder.getItemcomment().setText(items.get(position).getItemcomment());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
