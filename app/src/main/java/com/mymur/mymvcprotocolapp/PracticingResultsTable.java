package com.mymur.mymvcprotocolapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class PracticingResultsTable {
    private final static String TABLE_NAME = "PracticingResultsTable";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_STUDENT_ID = "_id_student";
    private final static String COLUMN_TRIAL_ID = "_id_trial";
    private final static String COLUMN_RESULT_CODE = "_id_resCode";
    private static String DATE = "_date";
    private final static Date date = new Date();
    private final static String dateStr = date.toString();


    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STUDENT_ID + " INTEGER, " + COLUMN_TRIAL_ID + " INTEGER, " +
                COLUMN_RESULT_CODE + " INTEGER, " + DATE + " TEXT );");
    }

    public static void addPracticing(int studentId, int trialId, int resCode, SQLiteDatabase database) {
        ContentValues values = new ContentValues();

        String dateStr = date.toString();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_TRIAL_ID, trialId);
        values.put(COLUMN_RESULT_CODE, resCode);
        values.put(DATE, dateStr);
        database.insert(TABLE_NAME, null, values);
    }



}
