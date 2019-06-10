package com.example.assignment4.background;

import android.content.Context;
import android.os.AsyncTask;

import com.example.assignment4.database.StudentDatabaseHelper;
import com.example.assignment4.fragment.StudentDetailsFragment;
import com.example.assignment4.helper.Constants;

public class StudentAsyncTaskHelper extends AsyncTask<String,Void,String> {

    private Context mContext;
    private String mResult;
    static private StudentDetailsFragment mFragment = new StudentDetailsFragment();

    public StudentAsyncTaskHelper(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        StudentDatabaseHelper myDb = new StudentDatabaseHelper(mContext);
        String actionOnDatabase = params[0];
        String name = params[Constants.ASYNC_PARAM1];
        String roll = params[Constants.ASYNC_PARAM2];
        String className = params[Constants.ASYNC_PARAM3];
        if(actionOnDatabase.equals(Constants.ADD)) {
            boolean isInserted = myDb.insertData(name, roll, className);
            System.out.println("hellooo"+actionOnDatabase+"  "+Constants.ADD);
            if (isInserted) {
                mResult = Constants.DB_ASYNCTASK_ADD_SUCCESS;
            }
            else {
                mResult = Constants.DB_ASYNCTASK_ADD_FAIL;
            }
        }
        else if(actionOnDatabase.equals(Constants.DELETE)){
            Integer itemsDeleted = myDb.deleteData(roll);
            if(itemsDeleted > Constants.MIN_ITEMS_DELETED) {
                mResult = Constants.DB_ASYNCTASK_DELETE_SUCCESS;
            }
            else {
                mResult = Constants.DB_ASYNCTASK_DELETE_FAIL;
            }
        }
        else if(actionOnDatabase.equals(Constants.EDIT)){
            boolean mResultFromDb = myDb.editData(name,roll,className);
            if(mResultFromDb) {
                mResult = Constants.DB_ASYNCTASK_EDIT_SUCCESS;
            }
            else {
                mResult = Constants.DB_ASYNCTASK_EDIT_FAIL;
            }
        }
        return mResult;
    }

    @Override
    protected void onPostExecute(String mResult) {
        mFragment.resultFromAsync(mContext,mResult);
    }
}