package com.mymur.mymvcprotocolapp;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

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

}
