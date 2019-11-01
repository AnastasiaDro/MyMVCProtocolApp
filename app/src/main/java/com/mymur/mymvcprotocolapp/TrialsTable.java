package com.mymur.mymvcprotocolapp;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

//класс таблицы проб
public class TrialsTable {
    private final static String TABLE_NAME = "Trials";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";

    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT);");
    }

    public static void addTrial(String trialName, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, trialName);
        database.insert(TABLE_NAME, null, values);
    }

    public static void addTrialIfNotExists(String trialName, SQLiteDatabase database){
//        if
//
//        addTrial(trialName, database);
    }

}