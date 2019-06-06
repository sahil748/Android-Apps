package com.example.assignment4.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.assignment4.R;
import com.example.assignment4.fragments.StudentDetailsFragment;

public class StudentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * @param intent is recieved from the student list fragment which consists of a bundle
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        StudentDetailsFragment fragment = StudentDetailsFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.activity_studentdetails_frameLayout, fragment);
        fragmentTransaction.commit();

    }
}