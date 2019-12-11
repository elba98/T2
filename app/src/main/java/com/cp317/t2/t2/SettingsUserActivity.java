package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsUserActivity extends AppCompatActivity {
    private Button changePassword_button;
    private Button save_button;
    private FirebaseAuth mAuth;
    private EditText firstName_editText, lastName_editText, phoneNumber_editText, postalCode_editText, program_editText, school_editText;
    private DatabaseReference usersDatabase;
    private EditText add_course_editText;
    private Switch onlinePreference_switch;
    private Switch inPersonPreference_switch;
    private ListView course_listView;
    private ArrayList<String> courseList = new ArrayList<>();
    private CourseListAdapter adapter;
    private FloatingActionButton plus_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_user);

        firstName_editText = (EditText) findViewById(R.id.firstName_editText);
        lastName_editText = (EditText) findViewById(R.id.lastName_editText);
        phoneNumber_editText = (EditText) findViewById(R.id.phoneNumber_editText);
        postalCode_editText = (EditText) findViewById(R.id.postalCode_editText);
        program_editText = (EditText) findViewById(R.id.program_editText);
        changePassword_button = (Button) findViewById(R.id.changePassword_button);
        school_editText = (EditText) findViewById(R.id.school_editText);

        fillFieldsFromDatabase();

        phoneNumber_editText = (EditText) findViewById(R.id.phoneNumber_editText);
        phoneNumber_editText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());


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
                save_data(validateInfo());
            }

        });

        plus_button = (FloatingActionButton) findViewById(R.id.plus_button);
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });

    }

    public void save_data(Boolean valid){
        if(!valid) { return; }
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

            school_editText = (EditText) findViewById(R.id.school_editText);
            String school = school_editText.getText().toString().trim();


            mAuth = FirebaseAuth.getInstance();
            usersDatabase = FirebaseDatabase.getInstance().getReference("users");
            String userId = mAuth.getCurrentUser().getUid();
            usersDatabase.child(userId).child("userFirstName").setValue(fname);
            usersDatabase.child(userId).child("userLastName").setValue(lname);
            usersDatabase.child(userId).child("userPostalCode").setValue(pcode);
            usersDatabase.child(userId).child("userPhoneNumber").setValue(pnum);
            usersDatabase.child(userId).child("program").setValue(program);
            usersDatabase.child(userId).child("school").setValue(school);

            String courses = "";
            // Save courses here
            System.out.println(courseList.toString());
            for (String course : courseList) {
                System.out.println("Course: "+course);
                courses = courses+course+" ";
            }
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

                    String school = dataSnapshot.child("school").getValue(String.class);
                    school_editText.setText(school);

                    fillCourses();

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

    private Boolean validateInfo() {
        EditText textFirstName = (EditText) findViewById(R.id.firstName_editText);
        EditText textLastName = (EditText) findViewById(R.id.lastName_editText);
        EditText textPhoneNumber = (EditText) findViewById(R.id.phoneNumber_editText);
        EditText textPostalCode = (EditText) findViewById(R.id.postalCode_editText);
        EditText school_editText = (EditText) findViewById(R.id.school_editText);



        String fName = textFirstName.getText().toString().trim();
        String lName = textLastName.getText().toString().trim();
        String pNumber = textPhoneNumber.getText().toString().trim();
        String pCode = textPostalCode.getText().toString().trim();
        String school = school_editText.getText().toString().trim();

        if (TextUtils.isEmpty((fName))) {
            Toast.makeText(getApplicationContext(), "First name can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!fName.matches("[a-zA-Z]+")) {
            Toast.makeText(getApplicationContext(), "First name must contain letters only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty((lName))) {
            Toast.makeText(getApplicationContext(), "Last name can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!lName.matches("[a-zA-Z]+")) {
            Toast.makeText(getApplicationContext(), "Last name must contain letters only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pNumber.length() != 14 && pNumber.length() != 16) {
            Toast.makeText(getApplicationContext(), "Phone number must contain numbers only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pCode.matches("^((\\d{5}-\\d{4})|(\\d{5})|([a-zA-Z]\\d[a-zA-Z]\\s?\\-?\\d[a-zA-Z]\\d))$")) {
            Toast.makeText(getApplicationContext(), "Postal code must be alpha numeric and contain a space", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!school.isEmpty() && !school.matches("[a-zA-Z]+")) {
            Toast.makeText(getApplicationContext(), "School must only contain letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }




    private void fillCourses() {
        course_listView = (ListView) findViewById(R.id.course_listView);
        adapter = new CourseListAdapter(SettingsUserActivity.this,R.layout.custom_course_list, courseList);
        course_listView.setAdapter(adapter);

        try{
            mAuth = FirebaseAuth.getInstance();
            usersDatabase = FirebaseDatabase.getInstance().getReference("users");
            String userId = mAuth.getCurrentUser().getUid();
            Log.d("UserId:", userId);
            usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot);
                    String courses = dataSnapshot.child("courses").getValue(String.class);
                    if(courses != null) {
                        courseList.addAll(Arrays.asList(courses.split(" ")));
                        adapter.notifyDataSetChanged();
                    }
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

//        course_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                courseList.remove(i);
//                adapter.notifyDataSetChanged();
//            }
//        });

//        FloatingActionButton delete_button = (FloatingActionButton) course_listView.findViewById(R.id.delete_button);

    }
    //TODO: Save scroll location and restore upon returning to view

    private void addCourse() {
        courseList.add("");
        adapter.notifyDataSetChanged();
    }
}

