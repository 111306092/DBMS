package com.example.dbms.cart;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

public class cartItem_viewholder extends RecyclerView.ViewHolder{
    private TextView itemname;
    private ImageButton itemphoto;
    private Button checkbutton;

    public cartItem_viewholder(@NonNull View itemView){

        super(itemView);
        itemname = itemView.findViewById(R.id.cartname);
        checkbutton = itemView.findViewById(R.id.cancelbutton);

    }

    public TextView getItemname() {
        return itemname;
    }

    public ImageButton getItemphoto() {
        return itemphoto;
    }

    public Button getCheckbutton() {
        return checkbutton;
    }
}
