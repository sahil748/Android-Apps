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


public class OtpActivity extends AppCompatActivity {

    protected EditText digit1,digit2,digit3,digit4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

       setText();
       onClickResendButton();
       onClickSubmitButton();
       autoFocusChange();
    }


    private void setText()                                                                           //Set text to views using id
    {
        TextView header1=(TextView)findViewById(R.id.otp_tv_header_1);
        header1.setText(R.string.otp_tv_header_1);

        TextView header2=(TextView)findViewById(R.id.otp_tv_header_2);
        header2.setText(R.string.otp_tv_header_2);

        TextView header3=(TextView)findViewById(R.id.otp_tv_header_3);
        header3.setText(R.string.otp_tv_header_3);
    }

    public void onClickResendButton()                                                                //hides keyboard and resend the otp on clicking resend button and reset otp digits to null.
    {
        Button resendButton=(Button)findViewById(R.id.otp_btn_resend);
        resendButton.setText(R.string.otp_btn_resend);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                                                   //hides keyboard
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                digit1.setText("");
                digit2.setText("");
                digit3.setText("");
                digit4.setText("");
                digit1.requestFocus();
                Snackbar snackbar = Snackbar.make(view, "otp sent again", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }
    public void onClickSubmitButton()                                                                //move to login screen after validating otp and if not valid showe message and reset otp to null using onClickListener
    {
        Button submitButton=(Button)findViewById(R.id.otp_btn_submit);
        submitButton.setText(R.string.otp_btn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (validateOtp(view)) {
                        Intent moveToLogin = new Intent(OtpActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        digit1.setText("");
                        digit2.setText("");
                        digit3.setText("");
                        digit4.setText("");
                        digit1.requestFocus();
                        Snackbar snackbar = Snackbar.make(view, "Incorrect OTP", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {

                }
            }
        });
    }
    public void autoFocusChange()                                                                    //changes focus after entering single digit to next digit and focus on previous digit after removing current digit of otp using request focus()
    {
        digit1 = (EditText) findViewById(R.id.otp_et_digit_1);
        digit2 = (EditText) findViewById(R.id.otp_et_digit_2);
        digit3 = (EditText) findViewById(R.id.otp_et_digit_3);
        digit4 = (EditText) findViewById(R.id.otp_et_digit_4);

        digit1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (digit1.getText().toString().length() == 1)
                {
                    digit2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)            //function overriding as it is member of abstract class
            {

                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s)                                                  //function overriding as it is member of abstract class
            {
                //  TODO Auto-generated method stub
            }
        });


        digit2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (digit2.getText().toString().length() == 1)
                {
                    digit3.requestFocus();
                }
                else if(digit2.getText().toString().length() == 0){
                    digit1.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {

                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        digit3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (digit3.getText().toString().length() == 1)
                {
                    digit4.requestFocus();
                }
                else if(digit3.getText().toString().length() == 0){
                    digit2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        digit4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
               if(digit4.getText().toString().length() == 0){
                    digit3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }
    private boolean validateOtp(View view)                                                            //returns true after validating the otp as must have 4 digits and display message if not validate
    {
        String first = digit1.getEditableText().toString();
        String second = digit2.getEditableText().toString();
        String third = digit3.getEditableText().toString();
        String fourth = digit4.getEditableText().toString();
        if (first.length() == 1 && second.length() == 1 && third.length() == 1 && fourth.length() == 1)
            return true;
        else {
            return false;
        }
    }


    public void moveToSignup(View view)                                                               //move to signup screen by destroying current page
    {
        finish();

    }
}
