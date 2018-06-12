package com.example.yanl1393.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact2018-1.db";
    public static final String TABLE_NAME = "Contact2018_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "Name";
    public static final String COLUMN_NAME_ADDRESS = "Address";
    public static final String COLUMN_NAME_PHONE = "Phone";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_CONTACT + " TEXT, " +
                    COLUMN_NAME_ADDRESS + " TEXT, " +
                    COLUMN_NAME_PHONE + " TEXT)";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("MyContactApp", "DatabaseHelper: contructed DataBaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyContactApp", "DatabaseHelper: creating database");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyContactApp", "DatabaseHelper: upgrading database");
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public boolean insertData(String name, String address, String phone) {
        Log.d("MyContactApp", "DatabaseHelper: inserting data");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_CONTACT,  name);
        contentValues.put(COLUMN_NAME_ADDRESS,  address);
        contentValues.put(COLUMN_NAME_PHONE,  phone);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Log.d("MyContactApp", "DatabsaseHelper: contact insert - FAILED");
            return false;
        } else {
            Log.d("MyContactApp", "DatabsaseHelper: contact insert - PASSED");
            return true;
        }
    }

    public Cursor getAllData() {
        Log.d("MyContactApp", "DatabaseHelper: pulling all records from db");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}
