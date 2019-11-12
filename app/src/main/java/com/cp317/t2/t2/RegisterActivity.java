package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by chill on 11/12/2019.
 */

public class RegisterActivity extends AppCompatActivity {
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.continue_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validate information
//                validateName();
                //TODO: open homepage
            }
        });
    }

//    private boolean validateName() {
//
//    }
//
//    private boolean validateEmail() {
//
//    }
//
//    private boolean validatePhoneNumber() {
//
//    }
//
//    private boolean validatePostalCode() {
//
//    }


    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}


