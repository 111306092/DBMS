package com.example.dbms.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.example.dbms.client.NotificationDialog;
import com.example.dbms.client.ReconnectDialog;
import com.example.dbms.map.Map;
import com.example.dbms.map.MapElement;
import com.example.dbms.map.Shelf;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    static Client client;
    ReconnectDialog dialog;
    LogoutWarning warning;
    NotificationDialog notification;
    static String user;
    String username;
    String selectedStore;
    ArrayList<String> targetItems;
    ArrayList<String> targetShelves;
    ArrayList<Shelf> order;
    Map map;
    boolean mapUpdated;

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

        client = new Client(getIntent().getStringExtra("IP"));
        map = new Map(client);
        mapUpdated = true;

        while (!client.getConnected()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Log.i("Debug", "Sleep Interrupted");
            }
        }

        dialog = new ReconnectDialog(client);
        warning = new LogoutWarning(this);
        notification = new NotificationDialog();
        targetItems = new ArrayList<>();
        targetShelves = new ArrayList<>();
        order = new ArrayList<>();
        selectedStore = "";

        String name = client.getUser(getIntent().getStringExtra("UserID"), getIntent().getStringExtra("Password"));
        if (!name.equals("NotFound")) {
            user = getIntent().getStringExtra("UserID");
            username = getIntent().getStringExtra("Username");
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, guideFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Guide")
                .commit();

        check();
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

    public void okayClick(View v){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, MainFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Main")
                .commit();
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
        if (!selectedStore.isEmpty()) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.MainFragment, MapFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("Map")
                    .commit();
        }
    }

    public void cart_click(View view){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, CartFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Cart")
                .commit();
    }

    public void shop1_click(View view){
        if (!selectedStore.equals("1")) {
            selectedStore = "1";
            targetItems.clear();
            order.clear();
            map.constructMap(selectedStore);
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, SearchFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Search")
                .commit();
    }

    public void shop2_click(View view){
        if (!selectedStore.equals("2")) {
            selectedStore = "2";
            targetItems.clear();
            order.clear();
            map.constructMap(selectedStore);
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, SearchFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Search")
                .commit();
    }

    public void shop3_click(View view){
        if (!selectedStore.equals("3")) {
            selectedStore = "3";
            targetItems.clear();
            order.clear();
            map.constructMap(selectedStore);
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.MainFragment, SearchFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("Search")
                .commit();
    }

    public void setTargetShelves() {
        targetShelves.clear();
        order.clear();

        ArrayList<String> shelfIDs = new ArrayList<>();
        for (String s: targetItems) {
            String[] temp = s.split(", ");

            String shelfID = client.getTargetShelf(selectedStore, temp[0]);

            targetShelves.add("S" + shelfID);
            shelfIDs.add(shelfID + "/AND/" + temp[0]);
        }

        client.updateHistoryCart(user, shelfIDs);

        mapUpdated = false;
    }

    public void showNotification(String type) {
        notification.setMessage(type);

        if (!notification.isAdded()) {
            notification.show(MainActivity.this.getFragmentManager(), "");
        }
    }

    public static ArrayList<String> updateHistoryRecord(){

        ArrayList<String> records = client.getHistoryCart(user);

        if(!records.isEmpty()){
            return records;
        }else{
            return null;
        }
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