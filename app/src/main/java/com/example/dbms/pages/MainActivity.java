package com.example.dbms.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dbms.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void personpage_click(View view){
        Intent it = new Intent(MainActivity.this,personal_page.class);
        startActivity(it);
        finish();
    }

    public void home_click(View view){
        Intent it = new Intent(MainActivity.this,MainActivity.class);
        startActivity(it);
        finish();
    }

    public void map_click(View view){
        Intent it = new Intent(MainActivity.this,map.class);
        startActivity(it);
        finish();
    }

    public void cart_click(View view){
        Intent it = new Intent(MainActivity.this, cart_page.class);
        startActivity(it);
        finish();
    }

    public void shop_click(View view){
        Intent it = new Intent(MainActivity.this,search_page.class);
        startActivity(it);
        finish();
    }


}