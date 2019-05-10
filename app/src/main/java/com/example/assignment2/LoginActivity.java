package com.example.assignment2;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmailText;
    private EditText mPasswordText;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setText();

        ImageView image = (ImageView) findViewById(R.id.login_iv_eye);
        image.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)                                                         //on clicking on eye image password will be visible till we press eye image
            {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPasswordText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        mPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }

        });
    }
     public  void setText()                                                                           //set text to all views using id
     {
         TextView login_tv_header = (TextView) findViewById(R.id.login_tv_header);
         login_tv_header.setText(R.string.login_tv_header);

         Button login_btn_log_in = (Button) findViewById(R.id.login_btn_log_in);
         login_btn_log_in.setText(R.string.login_btn_log_in);

         mEmailText = (EditText) findViewById(R.id.login_tv_email_id);
         mEmailText.setHint(getString(R.string.login_email_hint));

         mPasswordText = (EditText) findViewById(R.id.login_tv_password_id);
         mPasswordText.setHint(getString(R.string.login_password_hint));

         TextView notRegestered = (TextView) findViewById(R.id.login_tv_not_registered);
         notRegestered.setText(R.string.login_tv_not_registered);

         TextView regester = (TextView) findViewById(R.id.login_tv_register);
         regester.setText(R.string.login_tv_resister);
     }
        private static final Pattern EMAIL =                                                          //define email pattern
                Pattern.compile("^" +
                        "(?=.*[a-zA-Z])" +
                        "(?=.*[@#$%^&+=])" +
                        "(?=\\S+$)" +
                        "(?=.*[.])"+
                        ".{5,}" +
                        "$");


        private boolean validateEmail(View view)                                                       //validating email as  it must follow email. it must contain alphabets,@,. and not contai spaces
        {
            String email = mEmailText.getEditableText().toString().trim();
            if (email.isEmpty()) {
                displaySnackbar(view, "Please Enter email id");
                return false;
            }
                                                                                                        // EMAIL is the pattern for the regex validation for email id
            else if (!EMAIL.matcher(email).matches()) {
                displaySnackbar(view, "Invalid Email id");
                return false;
            }
            else if (email.indexOf(".") - email.indexOf("@") < 1) {
                displaySnackbar(view, "Invalid Email id");
                return false;
            }
            else
                return true;
        }

    private boolean validatePassword(View view)                                                        //validates password must not empty
    {
        String password = mPasswordText.getEditableText().toString().trim();
        if(password.isEmpty())
        {
            displaySnackbar(view,"Please enter Password");
            return false;
        }
        else
            return true;
    }

    public void login(View view) throws InterruptedException                                         //validates email and password by calling their validate functions and displays message
    {
        if(!(validateEmail(view)&& validatePassword(view)))
            return;
        else{
            displaySnackbar(view,"Login successful");
        }
    }


    public void displaySnackbar(View view, String displayStr)                                         //show  argument string to user
    {
        closeKeyboard();

        Snackbar.make(view, displayStr, Snackbar.LENGTH_SHORT).show();
    }


        public void closeKeyboard()                                                                     //hides keyboard to show snackbar message
        {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
        public void register (View view)                                                               //move to signup screen
        {
            Intent moveToSignup = new Intent(LoginActivity.this, SignUp.class);
            startActivity(moveToSignup);
        }
    }
