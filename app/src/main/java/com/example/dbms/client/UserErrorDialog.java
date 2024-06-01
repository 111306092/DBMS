package com.example.dbms.client;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class UserErrorDialog extends DialogFragment {
    private String errorMessage;

    public UserErrorDialog() {

    }

    @SuppressLint("ValidFragment")
    public UserErrorDialog(String type) {
        if (type.equals("Login")) {
            errorMessage = "Account Doesn't Exist, Please Register";
        } else {
            errorMessage = "Account Exist, Please Login";
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMessage)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
