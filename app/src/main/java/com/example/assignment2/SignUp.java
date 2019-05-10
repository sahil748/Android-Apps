package com.example.assignment2;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    private  EditText mNameText;
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
            Snackbar snackbar = Snackbar.make(view,"Please enter name",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        else if(name.length()<3){
            Snackbar snackbar = Snackbar.make(view,"name too short",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        else if(!name.matches( "[a-zA-Z]*" )){
            Snackbar snackbar = Snackbar.make(view,"Invalid name",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }
    private boolean validGender(View view)                                                            //validates gender.it must be male ,female or other,and should not empty
    {
        String gender = mGenderText.getEditableText().toString().trim();
        gender.toLowerCase();

        if(gender.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"Please Enter gender", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        else if(!(gender.matches("[mfMF]") | gender.matches("male") | gender.matches("MALE") | gender.matches("female") | gender.matches("OTHER")| gender.matches("other")| gender.matches("FEMALE"))){
            Snackbar snackbar = Snackbar.make(view,"Invalid gender input",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    private boolean validUser(View view)                                                             //validates  userType.it should not be empty,contains only alphabets
    {
        String userType = mUserTypeText.getEditableText().toString().trim();

        if(userType.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"Please enter user type",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        else if(!userType.matches( "[a-zA-Z]*" )){
            Snackbar snackbar = Snackbar.make(view,"Enter alphabets only in user type",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    private boolean validOccupation(View view)                                                        //validates  occupation.it should not be empty,contains only alphabets
    {
        String occupation = mOccupationText.getEditableText().toString().trim();

        if(occupation.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"please enter occupation",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        else if(!occupation.matches( "[a-zA-Z]*" )){
            Snackbar snackbar = Snackbar.make(view,"Please enter alphabets only in occation",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }


    public void moveToOtpScreen(View view)                                                            //move to otp screen after validating every edit text by calling their function
    {
        if(!(validName(view) && validGender(view) && validUser(view) && validOccupation(view)))
            return;
        else{

            Intent moveToOtp = new Intent(SignUp.this, OtpActivity.class);
        startActivityForResult(moveToOtp,0);

        }

    }                                                                                                 //move to login activity by destroying current page
    public void moveToLogin(View view)
    {
        finish();

    }
}
