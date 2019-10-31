package com.mymur.mymvcprotocolapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ResCodeTable {
    private final static String TABLE_NAME = "ResCodeTable";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";

    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT);");
    }

    public static void addAllResNames(String [] resultNames, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < resultNames.length; i++) {
            values.put(COLUMN_NAME, resultNames[i]);
            database.insert(TABLE_NAME, null, values);
            values.clear();
        }
    }
}