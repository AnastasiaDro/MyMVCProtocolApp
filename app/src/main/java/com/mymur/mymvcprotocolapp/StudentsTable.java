package com.mymur.mymvcprotocolapp;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

//класс таблицы студентов
public class StudentsTable {
    private final static String TABLE_NAME = "Students";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";



    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT);");
    }

    public static void addStudent(String studentName, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, studentName);
        database.insert(TABLE_NAME, null, values);
    }

    public static HashMap<Integer, String> getAllStudentsNames(SQLiteDatabase database){
        HashMap<Integer, String> studentsHashMap = new HashMap<>();
       // Cursor myCursor = database.rawQuery("select name from Students", null);
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME,  null);
        int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
        int nameIndex = myCursor.getColumnIndexOrThrow(COLUMN_NAME);

        while (myCursor.moveToNext()) {
            studentsHashMap.put(myCursor.getInt(idIndex), myCursor.getString(nameIndex));
            System.out.println(" добавили студента");
        }
        return studentsHashMap;

    }

}
