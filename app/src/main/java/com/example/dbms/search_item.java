package com.example.dbms;


public class search_item  {

    String itemname;

    public search_item(String itemname){
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
