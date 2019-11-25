package com.cp317.t2.t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TuteeProfileActivity extends AppCompatActivity {
    private Button dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutee_profile);

        dashboard = (Button) findViewById(R.id.dashboard_button);

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TuteeProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }


    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

}
