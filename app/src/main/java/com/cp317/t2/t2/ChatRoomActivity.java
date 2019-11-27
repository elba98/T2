package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ChatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

/*
This will be for the send button
        send_button =(android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                putText();
            }
        });

 */
    }
/*
    public void putText() {
        Intent intent =
        startActivity(intent);
    }
*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}
