package com.mymur.mymvcprotocolapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class PracticingResultsTable {
    private final static String TABLE_NAME = "PracticingResultsTable";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_STUDENT_ID = "_id_student";
    private final static String COLUMN_TRIAL_ID = "_id_trial";
    private final static String COLUMN_RESULT_CODE = "_id_resCode";
    private static String DATE = "_date";
    private final static Date date = Calendar.getInstance().getTime();
    private final static String dateStr = date.toString();


    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STUDENT_ID + " INTEGER, " + COLUMN_TRIAL_ID + " INTEGER, " +
                COLUMN_RESULT_CODE + " INTEGER, " + DATE + " TEXT );");
    }

    public static void addPracticing(int studentId, int trialId, int resCode, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_TRIAL_ID, trialId);
        values.put(COLUMN_RESULT_CODE, resCode);
        values.put(DATE, dateStr);
        database.insert(TABLE_NAME, null, values);
    }


    //получаем массив ID-шников проб,используемых  студентом с передаваемым айди
    public static ArrayList <Integer> getStudentTrialsIDArray(int studentID, SQLiteDatabase database) {
        ArrayList <Integer> studentTrialsIDArray = new ArrayList<>();
        //Set нужен для того, чтобы добавлять только уникальные значения ID-щников в список
        HashSet <Integer> studentTrialsSet = new HashSet<>();
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_TRIAL_ID + "  FROM "  +TABLE_NAME + " WHERE " + COLUMN_STUDENT_ID +"= :" + studentID,  null);
    //    int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID);
        int trialId = myCursor.getColumnIndexOrThrow(COLUMN_TRIAL_ID);

        while (myCursor.moveToNext()) {
            studentTrialsSet.add(myCursor.getInt(trialId));
            System.out.println(" добавили пробу");
        }
        studentTrialsIDArray.addAll(studentTrialsSet);
        return studentTrialsIDArray;
    }





}
