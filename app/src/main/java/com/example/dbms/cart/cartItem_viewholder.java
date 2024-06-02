package com.example.dbms.cart;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;

import java.util.ArrayList;

public class cartItem_viewholder extends RecyclerView.ViewHolder{
    private TextView itemname;
    private ImageButton itemphoto;
    private Button checkbutton;
    private ArrayList<String> targetItems;
    private cartItem_adapter parentAdaptor;

    public cartItem_viewholder(@NonNull View itemView, ArrayList<String> targetItems, cartItem_adapter parentAdaptor){
        super(itemView);
        this.targetItems = targetItems;
        this.parentAdaptor = parentAdaptor;

        itemname = itemView.findViewById(R.id.cartname);
        checkbutton = itemView.findViewById(R.id.cancelbutton);
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String removeString = "";
                for (String s: targetItems) {
                    if (s.equals(itemname.getText().toString())) {
                        removeString = s;
                    }
                }

                targetItems.remove(removeString);

                parentAdaptor.changeItems(targetItems);
                parentAdaptor.notifyDataSetChanged();
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
