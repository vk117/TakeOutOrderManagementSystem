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
                        DBSchema.TIME + "," +
                        "image" + " " + "BLOB" +
                ")"
        );

        db.execSQL("create table " + DBSchema.TABLE3_NAME + "(" +
                "_id integer primary key autoincrement, " +
                DBSchema.ORDERED_BY + "," +
                DBSchema.QUANTITY + "," +
                DBSchema.UNIT_PRICE + "," +
                DBSchema.PREP_TIME + "," +
                DBSchema.ITEM_NAME +
                ")"
        );

        db.execSQL("create table " + DBSchema.TABLE4_NAME + "(" +
                "_id integer primary key autoincrement, " +
                DBSchema.ORDERED_BY2 + "," +
                DBSchema.PREP_TIME2  + "," +
                DBSchema.STATUS + "," +
                DBSchema.ITEM_NAME2 +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DBSchema.TABLE_NAME );
        db.execSQL("drop table if exists " + DBSchema.TABLE2_NAME);
        db.execSQL("drop table if exists " + DBSchema.TABLE3_NAME);
        db.execSQL("drop table if exists " + DBSchema.TABLE4_NAME);

        onCreate(db);
    }
}
