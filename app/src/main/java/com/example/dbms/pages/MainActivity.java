package com.example.dbms.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, PersonalFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Personal")
                .commit();
    }

    public void home_click(View view){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, MainFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Main")
                .commit();
    }

    public void map_click(View view){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, MapFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Map")
                .commit();
    }

    public void cart_click(View view){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, CartFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Cart")
                .commit();
    }

    public void shop_click(View view){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, SearchFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Search")
                .commit();
    }


}