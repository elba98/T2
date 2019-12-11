package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by chill on 11/12/2019.
 */

public class HomepageActivity extends AppCompatActivity {
    private Button tutorLogin_Button, tuteeLogin_Button, register_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        tutorLogin_Button = (Button) findViewById(R.id.tutorLogin_button);
        //tuteeLogin_Button = (Button) findViewById(R.id.tuteeLogin_button);
        register_Button = (Button) findViewById(R.id.register_button);
        tutorLogin_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity("Tutor");
            }
        });
//        tuteeLogin_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openLoginActivity("Tutee");
//            }
//        });
        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }
    public void openLoginActivity(String userType) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userType",userType);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
