package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MessageActivity extends AppCompatActivity {
    private Button back_button, first, second, third, fourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        first =(Button) findViewById(R.id.messageOne);
        first.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openChatRoom();
            }
        });

        second =(Button) findViewById(R.id.messageTwo);
        second.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openChatRoom();
            }
        });

        third =(Button) findViewById(R.id.messageThree);
        third.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openChatRoom();
            }
        });

        fourth =(Button) findViewById(R.id.messageFour);
        fourth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openChatRoom();
            }
        });

        back_button =(Button) findViewById(R.id.messageBack_button);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });
        }

    public void openChatRoom() {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        startActivity(intent);
    }

    public void openHomePage() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

}
