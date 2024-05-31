package com.example.dbms.search_item;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

public class searchItem_viewholder extends RecyclerView.ViewHolder {

    private TextView itemname;
    private ImageButton itemphoto;
    private Button checkbutton;

    public searchItem_viewholder(@NonNull View itemView){

        super(itemView);
        itemname = itemView.findViewById(R.id.search_itemname);
        itemphoto = itemView.findViewById(R.id.info_button);
        checkbutton = itemView.findViewById(R.id.search_button);

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
