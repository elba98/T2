package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {
    private Button profile, tutorHistory, paymentHistory;
    private ImageView settings;
    private ImageButton chat;
    private TextView person;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loadUserInfo();

        profile = (Button) findViewById(R.id.profile_button);
        tutorHistory = (Button) findViewById(R.id.tutorHistory_button);
        paymentHistory = (Button) findViewById(R.id.paymentHistory_button);
        settings = (ImageView) findViewById(R.id.settings_button);
        chat = (ImageButton) findViewById(R.id.chat_button);
        person = (TextView) findViewById(R.id.rick_textView);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, TutorProfileActivity.class);
                startActivity(intent);
            }
        });
        tutorHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(DashboardActivity.this, TutorProfileActivity.class);
//                startActivity(intent);
            }
        });
        paymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(DashboardActivity.this, TutorProfileActivity.class);
//                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, TuteeProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user.getEmail() != null) {
            String toastText = "Logged in as " + user.getEmail();
            Toast.makeText(this,toastText, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"No display name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, HomepageActivity.class);
//        startActivity(intent);
        finish();
        System.exit(0);
    }
}