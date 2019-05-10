package com.example.assignment2;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.assignment2.Init.displaySnackbar;

public class SignUp extends AppCompatActivity {
    private static EditText mNameText;
    private  EditText mGenderText;
    private  EditText mUserTypeText;
    private  EditText mOccupationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setText();
        setHintText();
    }
    public static View getNameText()
    {
        return mNameText;
    }
    public void setText()                                                                             //set text to textview using id
    {
        TextView header1 = (TextView) findViewById(R.id.signup_tv_header_1);
        header1.setText(R.string.signup_tv_header_1);

        TextView header2 = (TextView) findViewById(R.id.signup_tv_header_2);
        header2.setText(R.string.signup_tv_header_2);

        TextView differentAccount=(TextView)findViewById(R.id.signup_tv_different_account);
        differentAccount.setText(R.string.signup_tv_different_account);

        Button cont =(Button)findViewById(R.id.signup_btn_cont);
        cont.setText(R.string.signup_tv_continue);
    }

    public void setHintText()                                                                         //set hint in editText using id
    {
        mNameText=(EditText)findViewById(R.id.signup_et_name);
        mNameText.setHint(getString(R.string.signup_et_name_hint));

        mGenderText=(EditText)findViewById(R.id.signup_et_gender);
        mGenderText.setHint(getString(R.string.signup_et_gender_hint));

        mUserTypeText=(EditText)findViewById(R.id.signup_et_user_type);
        mUserTypeText.setHint(getString(R.string.signup_et_user_type_hint));

        mOccupationText=(EditText)findViewById(R.id.signup_et_occupation);
        mOccupationText.setHint(getString(R.string.signup_et_occupation_hint));


    }
    private boolean validName(View view)                                                         ////validates name edittext. it should not be empty,length >3,contains only alphabets
    {
        String name = mNameText.getEditableText().toString();
        if(name.isEmpty()){
            displaySnackbar(view,"Please enter name");
            mNameText.requestFocus();

            return false;
        }
        else if(name.length()<3){
            displaySnackbar(view,"name too short");
            mNameText.requestFocus();
            return false;
        }
        else if(!name.matches( "[a-z A-Z]*" )){
            displaySnackbar(view,"Invalid name");
            mNameText.requestFocus();
            return false;
        }
        else if(!(name.contains(" ")))
        {
            displaySnackbar(view,"Please enter last name");
            mNameText.requestFocus();
            return false;
        }
        return true;
    }
    private boolean validGender(View view)                                                            //validates gender.it must be male ,female or other,and should not empty
    {
        String gender = mGenderText.getEditableText().toString().trim();
        gender.toLowerCase();

        if(gender.isEmpty()){
            displaySnackbar(view,"Please Enter gender");
            mGenderText.requestFocus();
            return false;
        }

        else if(!(gender.matches("[mfMF]") | gender.matches("male") | gender.matches("MALE") | gender.matches("female") | gender.matches("OTHER")| gender.matches("other")| gender.matches("FEMALE"))){
            displaySnackbar(view,"Invalid gender input");
            mGenderText.requestFocus();

            return false;
        }
        return true;
    }

    private boolean validUser(View view)                                                             //validates  userType.it should not be empty,contains only alphabets
    {
        String userType = mUserTypeText.getEditableText().toString().trim();

        if(userType.isEmpty()){
            displaySnackbar(view,"Please enter user type");
            mUserTypeText.requestFocus();
            return false;
        }

        else if(!userType.matches( "[a-z A-Z]*" )){
            displaySnackbar(view,"Enter alphabets only in user type");
            mUserTypeText.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validOccupation(View view)                                                        //validates  occupation.it should not be empty,contains only alphabets
    {
        String occupation = mOccupationText.getEditableText().toString().trim();

        if(occupation.isEmpty()){
            displaySnackbar(view,"please enter occupation");
            mOccupationText.requestFocus();
            return false;
        }

        else if(!occupation.matches( "[a-z A-Z]*" )){
            displaySnackbar(view,"Please enter alphabets only in occation");
            mOccupationText.requestFocus();
            return false;
        }
        return true;
    }


    public void moveToOtpScreen(View view)                                                            //move to otp screen after validating every edit text by calling their function
    {
        if(!(validName(view) && validGender(view) && validUser(view) && validOccupation(view)))
        {
            showKeyboard();
            return;
        }
        else{

            Intent moveToOtp = new Intent(SignUp.this, OtpActivity.class);
            startActivityForResult(moveToOtp,0);

        }

    }
    public void moveToLogin(View view)                                                               //move to login activity by destroying current page
    {
        finish();

    }
    public void showKeyboard()                                                                        // shows keyboard after 2 seconds by using handler
    {
        final View view = this.getCurrentFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);

            }
        },1900);

    }
}