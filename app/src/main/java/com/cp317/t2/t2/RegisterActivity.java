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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;




/**
 * Created by chill on 11/12/2019.
 */

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    DatabaseReference databaseUsers;
    ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        register = (Button) findViewById(R.id.continue_button);
        editTextEmail = (EditText) findViewById(R.id.email_editText);
        editTextPassword = (EditText) findViewById(R.id.password_editText);

        users = new ArrayList<>();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validate information
                addUser();
                registerUser(validateInfo());
                //TODO: open homepage
            }
        });
    }

    private Boolean validateInfo() {
        EditText textFirstName = (EditText) findViewById(R.id.firstName_editText);
        EditText textLastName = (EditText) findViewById(R.id.lastName_editText);
        EditText textPhoneNumber = (EditText) findViewById(R.id.phoneNumber_editText);
        EditText textPostalCode = (EditText) findViewById(R.id.postalCode_editText);
        EditText textEmail = (EditText) findViewById(R.id.email_editText);
        EditText textPassword = (EditText) findViewById(R.id.password_editText);

        String fName = textFirstName.getText().toString().trim();
        String lName = textLastName.getText().toString().trim();
        String pNumber = textPhoneNumber.getText().toString().trim();
        String pCode = textPostalCode.getText().toString().trim();
        String eMail = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(TextUtils.isEmpty((fName))) {
            Toast.makeText(getApplicationContext(),"First name can not be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!fName.matches("[a-zA-Z]+")) {
            Toast.makeText(getApplicationContext(), "First name must contain letters only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty((lName))) {
            Toast.makeText(getApplicationContext(),"Last name can not be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!lName.matches("[a-zA-Z]+")) {
            Toast.makeText(getApplicationContext(), "Last name must contain letters only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pNumber.matches("[0-9]+")) {
            Toast.makeText(getApplicationContext(),"Phone number must contain numbers only",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pCode.matches("^((\\d{5}-\\d{4})|(\\d{5})|([a-zA-Z]\\d[a-zA-Z]\\s\\d[a-zA-Z]\\d))$")) {
            Toast.makeText(getApplicationContext(),"Postal code must be alpha numeric and contain a space",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(eMail)) {
            Toast.makeText(getApplicationContext(),"Email must not be empty",Toast.LENGTH_SHORT).show();
            return false;
        } else if (!eMail.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")) {
            Toast.makeText(getApplicationContext(),"Email invalid",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),"Password must not be empty",Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(),"Password must be at least 6 characters long",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser(Boolean validInfo) {
        if(validInfo) {
            String mEmail = editTextEmail.getText().toString().trim();
            String mPassword = editTextPassword.getText().toString().trim();

            progressDialog.setMessage("Registering user...");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(),"Registered successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(),"Could not register user", Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });
        }
    }
//    TODO: implement the switch to users homepage and set the correct content for that user
//    private void updateUI(Object o) {
//        Intent intent = Intent(this, )
//    }

    private void addUser() {
        //getting the values to save
        EditText textFirstName = (EditText) findViewById(R.id.firstName_editText);
        EditText textLastName = (EditText) findViewById(R.id.lastName_editText);
        String fName = textFirstName.getText().toString().trim();
        String lName = textLastName.getText().toString().trim();

        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our User
        String id = databaseUsers.push().getKey();

        //creating an User Object
        User user = new User(id, fName, lName);

        //Saving the User
        databaseUsers.child(id).setValue(user);


        //displaying a success toast
        Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
    }
    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}