package com.cp317.t2.t2;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
    private Button profile, tutorHistory, paymentHistory;
    private ImageView settings;
    private ImageButton chat;
    private TextView suggestedUsers;
    private ListView users;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersDatabase;

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
        suggestedUsers = (TextView) findViewById(R.id.suggestedUsers_textView);

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
            String toastText = "Logged in as " + user.getEmail();
            Toast.makeText(this,toastText, Toast.LENGTH_SHORT).show();
            setTitle();
            setSuggestedUsers();
        } else {
            Toast.makeText(this,"No display name", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSuggestedUsers() {
        users = (ListView) findViewById(R.id.users_listView);
        CustomAdapter customAdapter = new CustomAdapter();
        users.setAdapter(customAdapter);
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
                    String userType = dataSnapshot.child("userType").getValue(String.class);
                    String text;
                    if(userType.equals("Tutee")) {
                        text = "Suggested Tutors";
                    } else {
                        text = "Suggested Tutees";
                    }
                    suggestedUsers.setText(text);
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

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_list,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.avatar_imageView);
            TextView nameView = (TextView) view.findViewById(R.id.name_textView);
            TextView programView = (TextView) view.findViewById(R.id.program_textView);

            int[] IMAGES = {R.drawable.prof1, R.drawable.prof2};
            String[] NAMES = {"user1", "user2"};
            String[] programs = {"Computer Science", "Psychology"};

            imageView.setImageResource(IMAGES[i]);
            nameView.setText(NAMES[i]);
            programView.setText(programs[i]);

            return null;
        }
    }
}
