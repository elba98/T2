package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class TutorProfileActivity extends AppCompatActivity {
        private Button dashboard;
        private DatabaseReference usersDatabase;
        private FirebaseAuth mAuth;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tutor_profile);

            dashboard = (Button) findViewById(R.id.dashboard_button);
            final TextView textName =  (TextView) findViewById(R.id.name);
            final TextView programText = (TextView) findViewById(R.id.program);
            final TextView coursesText = (TextView) findViewById(R.id.courses);


            try{
                mAuth = FirebaseAuth.getInstance();
                usersDatabase = FirebaseDatabase.getInstance().getReference("users");
                String userId = mAuth.getCurrentUser().getUid();
                Log.d("UserId:", userId);
                usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot);
                        String fName = dataSnapshot.child("userFirstName").getValue(String.class);
                        textName.setText(fName);
                        String courses = dataSnapshot.child("courses").getValue(String.class);
                        coursesText.setText(courses);
                        String program = dataSnapshot.child("program").getValue(String.class);
                        programText.setText(program);

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
//            usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    System.out.println(dataSnapshot);
//                    String fName = dataSnapshot.child("userFirstName").getValue(String.class);
//                    textName.setText(fName);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(getApplicationContext(),"You must choose a user type",Toast.LENGTH_SHORT).show();
//                }
//            });



            dashboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TutorProfileActivity.this, DashboardActivity.class);
                    startActivity(intent);
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




