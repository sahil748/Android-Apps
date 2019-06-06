package com.example.assignment4.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.assignment4.R;

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {                                                   //hold screen for 3 seconds
            @Override
            public void run()                                                                         //move to login screen
            {
                Intent moveToStudentsList= new Intent(SplashActivity.this, StudentsListActivity.class);
                startActivity(moveToStudentsList);
                finish();
            }

        },DELAY_TIME);
    }
}
