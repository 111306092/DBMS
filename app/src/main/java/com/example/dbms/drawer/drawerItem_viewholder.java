package com.example.dbms.drawer;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

public class drawerItem_viewholder extends RecyclerView.ViewHolder{
    private TextView itemname;
    private ImageButton itemphoto;

    public drawerItem_viewholder(@NonNull View itemView) {
        super(itemView);
        itemname = itemView.findViewById(R.id.draweritemname);
        itemphoto = itemView.findViewById(R.id.draweritem_button);
    }

    public TextView getItemname() {
        return itemname;
    }

    public ImageButton getItemphoto() {
        return itemphoto;
    }
}
