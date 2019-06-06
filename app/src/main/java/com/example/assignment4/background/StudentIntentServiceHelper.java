package com.example.assignment4.background;

import android.app.IntentService;
import android.content.Intent;

import com.example.assignment4.database.StudentDatabaseHelper;
import com.example.assignment4.helper.Constants;

public class StudentIntentServiceHelper extends IntentService {

    private String mAction,mName,mRoll,mClassName,mAnswer;
    private StudentDatabaseHelper mStudentDb;
    private static final String mINTENT_SERVICE_NAME = Constants.INTENT_SERVICE_NAME;

    public StudentIntentServiceHelper() {
        super(mINTENT_SERVICE_NAME);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mStudentDb = new StudentDatabaseHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mAction = intent.getStringExtra(Constants.KEY);
        mName = intent.getStringExtra(Constants.STUDENT_NAME);
        mRoll = intent.getStringExtra(Constants.STUDENT_ROLLNO);
        mClassName = intent.getStringExtra(Constants.STUDENT_CLASS);
        if(mAction.equals(Constants.ADD)) {
            boolean result = mStudentDb.insertData(mName, mRoll, mClassName);
            if (result) {
                mAnswer = Constants.DB_INTENTSERVICE_ADD_SUCCESS;
            } else {
                mAnswer = Constants.DB_INTENTSERVICE_ADD_FAIL;
            }
        }
        else if(mAction.equals(Constants.DELETE)){
            Integer itemsDeleted = mStudentDb.deleteData(mRoll);
            if(itemsDeleted > Constants.MIN_ITEMS_DELETED) {
                mAnswer = Constants.DB_INTENTSERVICE_DELETE_SUCCESS;
            }
            else {
                mAnswer = Constants.DB_INTENTSERVICE_DELETE_FAIL;
            }
        }

        else if(mAction.equals(Constants.EDIT)){
            boolean resultFromDb = mStudentDb.editData(mName,mRoll,mClassName);
            if(resultFromDb) {
                mAnswer = Constants.DB_INTENTSERVICE_EDIT_SUCCESS;
            }
            else {
                mAnswer = Constants.DB_INTENTSERVICE_EDIT_FAIL;
            }
        }

        Intent broadcast = new Intent(Constants.BROADCAST_INTENTSERVICE_VALUE);
        broadcast.putExtra(Constants.RECEIVER_INTENTSERVICE_VALUE , mAnswer);
        this.sendBroadcast(broadcast);
    }
}
