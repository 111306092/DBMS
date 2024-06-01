package com.example.dbms.pages;

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
    private EditText accountText, passwordText;
    private Button registerButton, loginButton;
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

        client = new Client();

        while (!client.getConnected()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Log.i("Debug", "Sleep Interrupted");
            }
        }

        dialog = new ReconnectDialog(client);
        check();

        accountText = findViewById(R.id.accountname);
        passwordText = findViewById(R.id.password);

        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
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

    public void what_click(View view){
        if (!accountText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()) {
            if (client.getUser(accountText.getText().toString(),  passwordText.getText().toString())) {
                Intent it = new Intent(login_page.this,MainActivity.class);
                client.close();

                startActivity(it);
                super.onDestroy();
            } else {
                UserErrorDialog errorDialog = new UserErrorDialog("Login");
                errorDialog.show(login_page.this.getFragmentManager(), "");
            }
        }
    }
}