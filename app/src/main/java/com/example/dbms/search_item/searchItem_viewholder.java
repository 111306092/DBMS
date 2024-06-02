package com.example.dbms.search_item;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class searchItem_viewholder extends RecyclerView.ViewHolder {

    private TextView itemname;
    private ImageButton itemphoto;
    private Button checkbutton;
    private ArrayList<String> targetItems;

    public searchItem_viewholder(@NonNull View itemView, ArrayList<String> targetItems){
        super(itemView);
        this.targetItems = targetItems;
        itemname = itemView.findViewById(R.id.search_itemname);
        itemphoto = itemView.findViewById(R.id.info_button);
        checkbutton = itemView.findViewById(R.id.search_button);
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (targetItems.size() < 20 && !targetItems.contains(itemname.getText().toString())) {
                    targetItems.add(itemname.getText().toString());
                }
            }
        });
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
