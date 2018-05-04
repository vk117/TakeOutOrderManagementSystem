package com.example.varun.snapsauce.datababse;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "snapsauce.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + DBSchema.TABLE_NAME + "(" +
                    "_id integer primary key autoincrement, " +
                        DBSchema.PHONE + "," +
                        DBSchema.NAME + "," +
                        DBSchema.EMAIL + "," +
                        DBSchema.PASSWORD  +
                ")"
        );

        db.execSQL("create table " + DBSchema.TABLE2_NAME + "(" +
                    "_id integer primary key autoincrement, " +
                        DBSchema.CATEGORY + "," +
                        DBSchema.NAME2 + "," +
                        DBSchema.PRICE + "," +
                        DBSchema.CALORIES + "," +
                        DBSchema.TIME +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DBSchema.TABLE_NAME );
        db.execSQL("drop table if exists " + DBSchema.TABLE2_NAME);
        onCreate(db);
    }
}
