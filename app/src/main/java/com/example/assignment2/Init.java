package com.example.assignment2;

import android.app.AppComponentFactory;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
public class Init extends AppCompatActivity                                                            //show argumented string to user
{
     public static void displaySnackbar(View view, String displayStr) {
        Snackbar.make(view, displayStr, Snackbar.LENGTH_SHORT).show();
    }
}

