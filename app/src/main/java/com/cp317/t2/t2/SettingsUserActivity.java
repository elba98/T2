package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText firstName_editText, lastName_editText, phoneNumber_editText, postalCode_editText, program_editText, courses_editText;
    private Button changePassword_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_user);

        changePassword_button = (Button) findViewById(R.id.changePassword_button);
        firstName_editText = (EditText) findViewById(R.id.firstName_editText);
        lastName_editText = (EditText) findViewById(R.id.lastName_editText);
        phoneNumber_editText = (EditText) findViewById(R.id.phoneNumber_editText);
        postalCode_editText = (EditText) findViewById(R.id.postalCode_editText);
        program_editText = (EditText) findViewById(R.id.program_editText);
        courses_editText = (EditText) findViewById(R.id.courses_editText);

        fillFieldsFromDatabase();

        changePassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsUserActivity.this, SettingsPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fillFieldsFromDatabase();
    }

    protected void fillFieldsFromDatabase() {
        DatabaseReference usersDatabase;
        try{
            mAuth = FirebaseAuth.getInstance();
            usersDatabase = FirebaseDatabase.getInstance().getReference("users");
            String userId = mAuth.getCurrentUser().getUid();
            Log.d("UserId:", userId);
            usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot);

                    String userFirstName = dataSnapshot.child("userFirstName").getValue(String.class);
                    firstName_editText.setText(userFirstName);

                    String userLastName = dataSnapshot.child("userLastName").getValue(String.class);
                    lastName_editText.setText(userLastName);

                    String userPhoneNumber = dataSnapshot.child("userPhoneNumber").getValue(String.class);
                    phoneNumber_editText.setText(userPhoneNumber);

                    String userPostalCode = dataSnapshot.child("userPostalCode").getValue(String.class);
                    postalCode_editText.setText(userPostalCode);

                    String program = dataSnapshot.child("program").getValue(String.class);
                    program_editText.setText(program);

                    String courses = dataSnapshot.child("program").getValue(String.class);
                    courses_editText.setText(courses);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"You must choose a user type",Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
