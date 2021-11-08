package com.example.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RegisterDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Disclaimer")
                .setMessage ("This is only for regular user, if you want to be our partnership, please contact us. Thank you.")
                .setNegativeButton("Contact us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"divergent@hotmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Partnership Request");
                        intent.putExtra(Intent.EXTRA_TEXT, "Hi, I am interested to be a partnership of your application.");

                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent, "Choose an email client"));


                    }
                })
                .setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }


                });



return builder.create();
    }
}
