package com.example.dbms.comment;



public class comment_item {

    String itemname;
    String itemcomment;

    public comment_item(String itemname,String itemcomment){
        this.itemname = itemname;
        this.itemcomment = itemcomment;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemcomment() {
        return itemcomment;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setItemcomment(String itemcomment) {
        this.itemcomment = itemcomment;
    }
}
