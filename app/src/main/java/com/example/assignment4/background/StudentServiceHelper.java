package com.example.assignment4.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.assignment4.database.StudentDatabaseHelper;
import com.example.assignment4.helper.Constants;

public class StudentServiceHelper extends Service {

    private String mName,mRoll,mClassName,mAction,mAnswer;
    protected StudentDatabaseHelper mStudentDb;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        mStudentDb = new StudentDatabaseHelper(this);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mAction = intent.getStringExtra(Constants.KEY);
        mName = intent.getStringExtra(Constants.KEY_NAME);
        mRoll = intent.getStringExtra(Constants.KEY_ROLL);
        mClassName = intent.getStringExtra(Constants.KEY_CLASS);
        if(mAction.equals(Constants.ADD)) {
            boolean result = mStudentDb.insertData(mName, mRoll, mClassName);
            if (result) {
                mAnswer = Constants.DB_SERVICE_ADD_SUCCESS;
            } else {
                mAnswer = Constants.DB_SERVICE_ADD_FAIL;
            }
        }

        else if(mAction.equals(Constants.DELETE)){
            Integer itemsDeleted = mStudentDb.deleteData(mRoll);
            if(itemsDeleted > Constants.MIN_ITEMS_DELETED) {
                mAnswer = Constants.DB_SERVICE_DELETE_SUCCESS;
            }
            else {
                mAnswer = Constants.DB_SERVICE_DELETE_FAIL;
            }
        }

        else if(mAction.equals(Constants.EDIT)){
            boolean resultFromDb = mStudentDb.editData(mName,mRoll,mClassName);
            if(resultFromDb) {
                mAnswer = Constants.DB_SERVICE_EDIT_SUCCESS;
            }
            else {
                mAnswer = Constants.DB_SERVICE_EDIT_FAIL;
            }
        }

        Intent broadcast = new Intent(Constants.BROADCAST_SERVICE_VALUE);
        broadcast.putExtra(Constants.RECEIVER_SERVICE_VALUE , mAnswer);
        this.sendBroadcast(broadcast);

        return super.onStartCommand(intent,flags,startId);
    }
}
