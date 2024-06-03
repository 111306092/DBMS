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
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;
import com.example.dbms.pages.SearchFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class searchItem_viewholder extends RecyclerView.ViewHolder {

    private TextView itemname;
    private ImageButton itemphoto;
    private Button checkbutton;

    String productInfo;

    public searchItem_viewholder(@NonNull View itemView, ArrayList<String> targetItems, SearchFragment fragment, DrawerLayout drawerLayout){
        super(itemView);
        itemname = itemView.findViewById(R.id.search_itemname);
        itemphoto = itemView.findViewById(R.id.info_button);
        checkbutton = itemView.findViewById(R.id.search_button);

        productInfo = "";

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
                String[] temp = productInfo.split("/AND/");

                TextView name = fragment.getView().findViewById(R.id.itemname);
                name.setText(temp[0]);

                TextView price = fragment.getView().findViewById(R.id.itemprice);
                if (!temp[3].equals("0")) {
                    int p = Integer.parseInt(temp[1]) - Integer.parseInt(temp[3]);
                    temp[1] = String.valueOf(p);

                    price.setTextColor(Color.RED);
                } else {
                    price.setTextColor(Color.BLACK);
                }
                price.setText(String.format("NT$ %s", temp[1]));

                TextView des = fragment.getView().findViewById(R.id.iteminfo);
                des.setText(temp[2]);

                drawerLayout.openDrawer(GravityCompat.START);
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
