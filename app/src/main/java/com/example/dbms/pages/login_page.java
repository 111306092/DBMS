package com.example.dbms.pages;

import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dbms.R;
import com.example.dbms.client.Client;
import com.example.dbms.client.ReconnectDialog;
import com.example.dbms.client.UserErrorDialog;

import java.util.concurrent.TimeUnit;

public class login_page extends AppCompatActivity {
    private EditText accountText, passwordText, usernameText, ipText;
    private Client client;
    private ReconnectDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        accountText = findViewById(R.id.accountname);
        passwordText = findViewById(R.id.password);
        usernameText = findViewById(R.id.UserName);
        ipText = findViewById(R.id.ipaddress);

        client = new Client(ipText.getText().toString());

        for (int i = 0; i < 10 && !client.getConnected(); i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Log.i("Debug", "Sleep Interrupted");
            }
        }

        dialog = new ReconnectDialog(client);
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
                                    dialog.show(login_page.this.getFragmentManager(), "");
                                    client.setIp(ipText.getText().toString());
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

    public void login_click (View view){
        if (!accountText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()) {
            String name = client.getUser(accountText.getText().toString(), passwordText.getText().toString());

            if (!name.isEmpty()) {
                Intent it = new Intent(login_page.this, MainActivity.class);
                it.putExtra("UserID", accountText.getText().toString());
                it.putExtra("Password", passwordText.getText().toString());
                it.putExtra("Username", name);
                it.putExtra("IP", ipText.getText().toString());
                client.close();

                startActivity(it);
                super.onDestroy();
            } else {
                UserErrorDialog errorDialog = new UserErrorDialog("Login");
                errorDialog.show(login_page.this.getFragmentManager(), "");
            }
        }
    }

    public void register_click(View view) {
        if (!accountText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty() && !usernameText.getText().toString().isEmpty()) {
            if (client.registerUser(accountText.getText().toString(), passwordText.getText().toString(), usernameText.getText().toString())) {
                UserErrorDialog errorDialog = new UserErrorDialog("Okay");
                errorDialog.show(login_page.this.getFragmentManager(), "");
            } else {
                UserErrorDialog errorDialog = new UserErrorDialog("Register");
                errorDialog.show(login_page.this.getFragmentManager(), "");
            }
        }
    }
}