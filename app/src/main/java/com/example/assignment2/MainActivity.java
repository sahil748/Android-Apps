package com.example.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       TextView splash_tv_text=(TextView)findViewById(R.id.splash_tv_id);
        splash_tv_text.setText(R.string.splash_tv_text);

        new Handler().postDelayed(new Runnable() {                                                   //hold screen for 3 seconds


            @Override

            public void run()                                                                         //move to login screen
            {

                Intent i = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(i);


                finish();

            }

        }, 3*1000);

    }
}
