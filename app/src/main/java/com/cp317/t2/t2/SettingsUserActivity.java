package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsUserActivity extends AppCompatActivity {
    private Button changePassword_button;
    private Button save_button;
    private FirebaseAuth mAuth;
    private EditText firstName_editText, lastName_editText, phoneNumber_editText, postalCode_editText, program_editText, courses_editText;
    private DatabaseReference usersDatabase;
    private EditText add_course_editText;
    private Switch onlinePreference_switch;
    private Switch inPersonPreference_switch;

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
        courses_editText = (EditText) findViewById(R.id.add_course_editText);

        fillFieldsFromDatabase();

        changePassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsUserActivity.this, SettingsPasswordActivity.class);
                startActivity(intent);
            }
        });

        save_button = (Button) findViewById(R.id.save_button);

        save_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                save_data();
            }

        });

    }

    public void save_data(){
        try{

            firstName_editText = (EditText) findViewById(R.id.firstName_editText);
            final String fname = firstName_editText.getText().toString().trim();

            lastName_editText = (EditText) findViewById(R.id.lastName_editText);
            String lname = lastName_editText.getText().toString().trim();

            phoneNumber_editText = (EditText) findViewById(R.id.phoneNumber_editText);
            String pnum = phoneNumber_editText.getText().toString().trim();

            postalCode_editText = (EditText) findViewById(R.id.postalCode_editText);
            String pcode = postalCode_editText.getText().toString().trim();

            program_editText = (EditText) findViewById(R.id.program_editText);
            String program = program_editText.getText().toString().trim();

            add_course_editText = (EditText) findViewById(R.id.add_course_editText);
            String courses = add_course_editText.getText().toString().trim();


            mAuth = FirebaseAuth.getInstance();
            usersDatabase = FirebaseDatabase.getInstance().getReference("users");
            String userId = mAuth.getCurrentUser().getUid();
            usersDatabase.child(userId).child("userFirstName").setValue(fname);
            usersDatabase.child(userId).child("userLastName").setValue(lname);
            usersDatabase.child(userId).child("userPostalCode").setValue(pcode);
            usersDatabase.child(userId).child("userPhoneNumber").setValue(pnum);
            usersDatabase.child(userId).child("program").setValue(program);
            usersDatabase.child(userId).child("courses").setValue(courses);

        }
        catch (Exception e){
            System.out.println(e);
        }
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
