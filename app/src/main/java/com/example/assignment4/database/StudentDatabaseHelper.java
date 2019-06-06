package com.example.assignment4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment4.helper.Constants;

public class StudentDatabaseHelper extends SQLiteOpenHelper {
    

    private static final String mDATABASE_NAME = Constants.DATABASE_NAME;
    private final String mTABLE_NAME = Constants.DB_TABLE_NAME;
    private final String mCOL_1 = Constants.DB_TABLE_COL1;
    private final String mCOL_2 = Constants.DB_TABLE_COL2;
    private final String mCOL_3 = Constants.DB_TABLE_COL3;
    private static int mDATABASE_VERSION = Constants.DATABASE_VERSION;

    public StudentDatabaseHelper(Context context) {
        super(context, mDATABASE_NAME, null, mDATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + mTABLE_NAME + " (NAME TEXT, ROLL TEXT PRIMARY KEY,CLASS TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + mTABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String roll, String className) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print("yeho "+name+roll+className);
        ContentValues contentValues = new ContentValues();
        contentValues.put(mCOL_1, name);
        contentValues.put(mCOL_2, roll);
        contentValues.put(mCOL_3, className);
        long result = db.insert(mTABLE_NAME, null, contentValues);
        if (result == Constants.DB_RESULT_FAIL) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + mTABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(mTABLE_NAME, "ROLL = ?", new String[]{roll});
    }

    public boolean editData(String name, String roll, String className) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mCOL_1, name);
        contentValues.put(mCOL_2, roll);
        contentValues.put(mCOL_3, className);
        int count = db.update(mTABLE_NAME, contentValues, "ROLL = ?", new String[]{roll});
        if (count > Constants.MIN_ITEMS_DELETED) {
            return true;
        } else {
            return false;
        }
    }
}
