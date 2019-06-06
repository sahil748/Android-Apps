package com.example.assignment4.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.assignment4.R;

public class util {
    private static final String mValidatesName =("[a-zA-Z][a-zA-Z ]+[a-zA-Z]$");                     //patterns to validate name                                  //validation pattern for name
    private static final String mValidatesRoll =("[0-9]*");                                            //rollno and class of students
    private static final String mValidatesClass =("[a-zA-Z]*" );

    /**
     *validates the data argumented
     * @param studentName    name of student entered
     * @param studentRoll    roll no and
     * @param studentClass   class of student
     * @return
     */
    public boolean validName(Context context, String studentName, String studentRoll, String studentClass){
        if(studentName.isEmpty()){
            showToast(context,R.string.error_enter_name);
            return false;
        }
        else if(!studentName.matches( mValidatesName)){
            showToast(context,R.string.error_invalid_name);
            return false;
        }
        if(studentRoll.isEmpty()){
            showToast(context,R.string.error_enter_rollno);
            return false;
        }
        else if(!studentRoll.matches( mValidatesRoll )){
            showToast(context,R.string.error_invalid_rollno);
            return false;
        }
        if(studentClass.isEmpty()){
            showToast(context,R.string.error_enter_class);
            return false;
        }
        else if(!studentClass.matches(mValidatesClass )){
            showToast(context,R.string.error_invalid_class);
            return false;
        }
        return true;
    }

    /**
     *               shows message on screen
     * @param message message to put on screen
     */
    public void showToast(Context context,int message)
    {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
    public void showToast(Context context,String message)
    {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
}
