package com.example.dbms.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.dbms.client.Client;
import com.example.dbms.client.LogoutWarning;
import com.example.dbms.client.ReconnectDialog;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Client client;
    ReconnectDialog dialog;
    LogoutWarning warning;
    String user, username;

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

        client = new Client();

        while (!client.getConnected()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Log.i("Debug", "Sleep Interrupted");
            }
        }

        dialog = new ReconnectDialog(client);
        warning = new LogoutWarning(this);
        check();

        String name = client.getUser(getIntent().getStringExtra("UserID"), getIntent().getStringExtra("Password"));
        if (!name.equals("NotFound")) {
            user = getIntent().getStringExtra("UserID");
            username = getIntent().getStringExtra("Username");
        }
    }

    public void check() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    while (true) {
                        try {
                            if (!client.getConnected()) {
                                if (!dialog.isAdded()) {
                                    dialog.show(MainActivity.this.getFragmentManager(), "");
                                    //client.setIp(ipInput.getText().toString());
                                }
                            }
                            Log.i("Client Status", String.format("%s", client.getConnected()));
                            this.wait(10000 * dialog.getMultiplier());
                        } catch (InterruptedException e) {
                            Log.i("Debug", "Wait Interrupted");
                        }
                    }
                }
            }
        });
        thread.start();
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

    public void logout() {
        Intent it = new Intent(MainActivity.this,login_page.class);
        client.close();

        startActivity(it);
        super.onDestroy();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (!warning.isAdded()) {
            warning.show(MainActivity.this.getFragmentManager(), "");
        }
    }
}