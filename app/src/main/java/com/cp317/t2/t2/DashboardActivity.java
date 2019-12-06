package com.cp317.t2.t2;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
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
    private ArrayList<User> userList = new ArrayList<User>();
    private UserListAdapter adapter;
    private String oppositeUserType;
    private EditText search_editText;

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
                    String userType;
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
//        user_listView = (ListView) findViewById(R.id.users_listView);
//        adapter = new UserListAdapter(this,R.layout.custom_list, userList);


        // Get users from database (if logged on user is tutor, get tutees and vise-versa)
        try{
            usersDatabase = FirebaseDatabase.getInstance().getReference("users");
            String userId = mAuth.getCurrentUser().getUid();
            Log.d("UserId:", userId);
            usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userType;
                    userType = dataSnapshot.child("userType").getValue(String.class);
                    if(userType.equals("Tutee")) {
                        oppositeUserType = "Tutor";
                    } else {
                        oppositeUserType = "Tutee";
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
        user_listView = (ListView) findViewById(R.id.users_listView);
        adapter = new UserListAdapter(DashboardActivity.this,R.layout.custom_list, userList);
        user_listView.setAdapter(adapter);

        // Query database and update adapter
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("userType").equalTo("Tutor");
        //query.addValueEventListener(
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);

       // user_listView.setAdapter(adapter);
        user_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = userList.get(i);
//                Toast.makeText(getApplicationContext(),user.getuId(),Toast.LENGTH_SHORT).show();

                //Open their profile
                Intent intent;
                if(oppositeUserType.equals("Tutor")) {
                    intent = new Intent(getApplicationContext(), TutorProfileActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), TuteeProfileActivity.class);
                }
                intent.putExtra("uID",user.getuId());
                startActivity(intent);
            }
        });

        search_editText = (EditText) findViewById(R.id.search_editText);

        search_editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                DashboardActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) {}
        });
    }
}