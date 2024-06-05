package com.example.dbms.client;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.dbms.pages.MainActivity;

@SuppressLint("ValidFragment")
public class NotificationDialog  extends DialogFragment {
    String message = "";
    @SuppressLint("ValidFragment")
    public NotificationDialog() {
    }

    public void setMessage(String type) {
        if (type.equals("Add")) {
            message = "Product Has Been Added To Cart";
        } else if (type.equals("Remove")) {
            message = "Product Has Been Removed From Cart";
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
