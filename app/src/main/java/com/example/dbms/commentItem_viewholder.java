package com.example.dbms;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class commentItem_viewholder extends RecyclerView.ViewHolder {

    private TextView itemname;
    private TextView itemcomment;
    private ImageView itemphoto;
    public commentItem_viewholder(@NonNull View itemView) {
        super(itemView);
        itemname = itemView.findViewById(R.id.com_name);
        itemphoto = itemView.findViewById(R.id.com_image);
        itemcomment = itemView.findViewById(R.id.user_com);
    }

    public TextView getItemname() {
        return itemname;
    }

    public TextView getItemcomment() {
        return itemcomment;
    }

    public ImageView getItemphoto() {
        return itemphoto;
    }
}
