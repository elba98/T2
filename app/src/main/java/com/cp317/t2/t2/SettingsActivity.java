package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    private Button changeName_button, changeEmail_button,changeLocation_button, back_button, signout_button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);

        mAuth = FirebaseAuth.getInstance();
        changeName_button = findViewById(R.id.changeName_button);
        changeEmail_button = findViewById(R.id.changeEmail_button);
        changeLocation_button = findViewById(R.id.changeLocation_button);
        back_button = findViewById(R.id.back_button);
        signout_button = findViewById(R.id.signout_button);



        changeName_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        changeEmail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        changeLocation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        signout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mAuth.signOut();
                    Toast.makeText(SettingsActivity.this, "Sign out successful", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(SettingsActivity.this, HomepageActivity.class);
                    startActivity(intent);

                }
                catch (Exception e){
                    Toast.makeText(SettingsActivity.this, "Error signing out", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}