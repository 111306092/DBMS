package com.example.dbms.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbms.R;
import com.example.dbms.drawer.drawerItem_adapter;
import com.example.dbms.drawer.drawer_item;

import java.util.ArrayList;

public class map extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button button;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.itemsinshelf);
        ArrayList<drawer_item> items = new ArrayList<drawer_item>();
        items.add(new drawer_item("1"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new drawerItem_adapter(getApplicationContext(),items));

        drawerLayout = findViewById(R.id.itemlist);
        button = findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }




    public void personpage_click(View view){
        Intent it = new Intent(map.this,personal_page.class);
        startActivity(it);
        finish();
    }

    public void map_click(View view){
        Intent it = new Intent(map.this,map.class);
        startActivity(it);
        finish();
    }

    public void home_click(View view){
        Intent it = new Intent(map.this,MainActivity.class);
        startActivity(it);
        finish();
    }

    public void cart_click(View view){
        Intent it = new Intent(map.this, cart_page.class);
        startActivity(it);
        finish();
    }
}