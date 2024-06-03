package com.example.dbms.search_item;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;
import com.example.dbms.comment.commentItem_adapter;
import com.example.dbms.comment.comment_item;
import com.example.dbms.pages.SearchFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class searchItem_viewholder extends RecyclerView.ViewHolder {

    private TextView itemname;
    private ImageButton itemphoto;
    private Button checkbutton;
    private ArrayList<String> targetItems;

    String productInfo;

    public searchItem_viewholder(@NonNull View itemView, SearchFragment fragment){
        super(itemView);
        itemname = itemView.findViewById(R.id.search_itemname);
        itemphoto = itemView.findViewById(R.id.info_button);
        checkbutton = itemView.findViewById(R.id.search_button);
        productInfo = "";

        this.targetItems = fragment.getTargetItems();

        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (targetItems.size() < 20 && !targetItems.contains(itemname.getText().toString())) {
                    targetItems.add(itemname.getText().toString());
                }
            }
        });

        itemphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.generateDrawerLayout(productInfo);
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

    public void setProductInfo(String info) {
        this.productInfo = info;
    }
}
