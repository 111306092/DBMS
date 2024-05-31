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
import com.example.dbms.comment.commentItem_adapter;
import com.example.dbms.comment.comment_item;

import java.util.ArrayList;

public class item_page extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.com_recyclerview);
        ArrayList<comment_item> items = new ArrayList<comment_item>();
        items.add(new comment_item("1","2"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new commentItem_adapter(getApplicationContext(),items));
    }

    public void personpage_click(View view){
        Intent it = new Intent(item_page.this,personal_page.class);
        startActivity(it);
        finish();
    }

    public void map_click(View view){
        Intent it = new Intent(item_page.this,map.class);
        startActivity(it);
        finish();
    }

    public void home_click(View view){
        Intent it = new Intent(item_page.this,MainActivity.class);
        startActivity(it);
        finish();
    }

    public void cart_click(View view){
        Intent it = new Intent(item_page.this, cart_page.class);
        startActivity(it);
        finish();
    }
}