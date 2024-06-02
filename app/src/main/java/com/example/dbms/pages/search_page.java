package com.example.dbms.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;
import com.example.dbms.search_item.searchItem_adapter;
import com.example.dbms.search_item.search_item;

import java.util.ArrayList;

public class search_page extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.search_recyclerview);
        ArrayList<search_item> items = new ArrayList<search_item>();
        items.add(new search_item("1"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new searchItem_adapter(getApplicationContext(),items, new ArrayList<>()));
    }

    public void personpage_click(View view){
        Intent it = new Intent(search_page.this,item_page.class);
        startActivity(it);
        finish();
    }

    public void map_click(View view){
        Intent it = new Intent(search_page.this,map.class);
        startActivity(it);
        finish();
    }

    public void home_click(View view){
        Intent it = new Intent(search_page.this,MainActivity.class);
        startActivity(it);
        finish();
    }

    public void cartpage_click(View view){
        Intent it = new Intent(search_page.this, cart_page.class);
        startActivity(it);
        finish();
    }
}