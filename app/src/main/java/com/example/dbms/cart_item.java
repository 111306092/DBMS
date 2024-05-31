package com.example.dbms;


import android.content.Intent;
import android.view.View;

public class cart_item  {

    String itemname;


    public cart_item(String itemname){
        this.itemname = itemname;

    }

    public String getItemname(){
        this.itemname=itemname;
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    }

