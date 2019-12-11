package com.cp317.t2.t2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsPasswordActivity extends AppCompatActivity {
    Button send_button;
    EditText email_editText;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_password);

        send_button = (Button) findViewById(R.id.send_button);
        email_editText = (EditText) findViewById(R.id.email_editText);
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String email = email_editText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        } else if (!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            Toast.makeText(getApplicationContext(), "Email invalid, please fix your email.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Sending Email...");
        progressDialog.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SettingsPasswordActivity.this, SettingsUserActivity.class);
                    startActivity(intent);
                } else {
                    progressDialog.hide();
                    Toast.makeText(SettingsPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingsUserActivity.class);
        startActivity(intent);
    }
}