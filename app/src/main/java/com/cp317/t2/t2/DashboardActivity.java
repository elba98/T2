package com.cp317.t2.t2;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private Button profile, tutorHistory, paymentHistory;
    private ImageView settings;
    private ImageButton chat;
    private TextView suggestedUsers, welcomeMessage;
    private ListView user_listView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersDatabase;
    private ArrayList<User> userList = new ArrayList<>();
    private String userType, oppositeUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loadUserInfo();

        profile = (Button) findViewById(R.id.profile_button);
        settings = (ImageView) findViewById(R.id.settings_button);
        chat = (ImageButton) findViewById(R.id.chat_button);
        suggestedUsers = (TextView) findViewById(R.id.suggestedUsers_textView);
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage_textView);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, TutorProfileActivity.class);
                startActivity(intent);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
        } else {
            loadUserInfo();
        }
    }

    private void loadUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user.getEmail() != null) {
//            String welcomeText = "Welcome " + user.getDisplayName();
//            Toast.makeText(this,welcomeText, Toast.LENGTH_SHORT).show();
//            welcomeMessage.setText(welcomeText);
            setTitle();
            setSuggestedUsers();
        } else {
            Toast.makeText(this,"No display name", Toast.LENGTH_SHORT).show();
        }
    }

    protected void setTitle() {
        try{
            usersDatabase = FirebaseDatabase.getInstance().getReference("users");
            String userId = mAuth.getCurrentUser().getUid();
            Log.d("UserId:", userId);
            usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot);
                    userType = dataSnapshot.child("userType").getValue(String.class);
                    String text;
                    if(userType.equals("Tutee")) {
                        text = "Suggested Tutors";
                    } else {
                        text = "Suggested Tutees";
                    }
                    suggestedUsers.setText(text);

                    text = "Logged in as " + dataSnapshot.child("userEMail").getValue(String.class);
                    welcomeMessage.setText(text);
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

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    private void setSuggestedUsers() {
        // Get users from database (if logged on user is tutor, get tutees and vise-versa)

//        usersDatabase.orderByChild("userType").equalTo("Tutor").limitToFirst(100).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println("amount of users = " + dataSnapshot.getChildrenCount());
//                for(int i=0; i<dataSnapshot.getChildrenCount(); i++) {
//                    DataSnapshot child = dataSnapshot.getChildren().iterator().next();
//                    String fName = child.child("userFirstName").getValue(String.class);
//                    String lName = child.child("userLirstName").getValue(String.class);
//                    String program = child.child("program").getValue(String.class);
//                    User user = new User(fName, lName, program);
//                    userList.add(user);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        User john = new User("elba3790mylaurier.ca", "John", "Tutor", "Tutor",
                "1234567890", "l1l 1l1");
        User bob = new User("elba3790mylaurier.ca", "Bob", "Tutee", "Tutee",
                "1234567890", "l1l 1l1");
        userList.add(john);
        userList.add(bob);
        user_listView = (ListView) findViewById(R.id.users_listView);
        UserListAdapter adapter = new UserListAdapter(this,R.layout.custom_list, userList);
        user_listView.setAdapter(adapter);
        user_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = userList.get(i);
                Toast.makeText(getApplicationContext(),user.getUserFirstName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
